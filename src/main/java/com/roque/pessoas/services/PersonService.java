package com.roque.pessoas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.roque.pessoas.models.Address;
import com.roque.pessoas.models.Person;
import com.roque.pessoas.repositories.IPersonRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonService {

    private final IPersonRepository personRepository;

    public PersonService(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public void addNewPerson(Person person) {
        Optional<Person> personOptional = personRepository.findByEmail(person.getEmail());
        // List<Address> addresses = person.getAddresses();

        if (personOptional.isPresent()) {
            throw new ResponseStatusException(
           HttpStatus.CONFLICT, "Email Already taken");
        }

        List<Address> personAddresses = person.getAddresses();
        ValidateAddressesList(personAddresses);

        personRepository.save(person);
    }

    public void deletePerson(long personId){
        boolean personExist = personRepository.existsById(personId);
        if (!personExist) {
            throw new ResponseStatusException(
           HttpStatus.NOT_FOUND, "Person with given Id does not exist");
        }
        personRepository.deleteById(personId);
    }

    @Transactional
    public void updatePerson(long personId, Person person){

        Person personToUpdate = personRepository.findById(personId)
            .orElseThrow(
                () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person with given Id does not exist"));
        
        personToUpdate.setName(person.getName());
        personToUpdate.setBirthDate(person.getBirthDate());
        personToUpdate.setEmail(person.getEmail());
        personToUpdate.setPhone(person.getPhone());
    }

    public Person getPersonById(long personId) {
        Person personById = personRepository.findById(personId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Person with given Id does not exist"));

            return personById;
    }

    @Transactional
    public void changeMainAddress(long personId, long addressId){

        Person personToChangeMainAddress = personRepository.findById(personId)
            .orElseThrow(
                () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person with given Id does not exist"));
        
        List<Address> addressesToChange = personToChangeMainAddress.getAddresses();

        for (Address address : addressesToChange) {
            if (address.getId() == addressId) {
                address.setMainAddress(true);
            } else if(address.getId() != addressId && address.getMainAddress()) {
                address.setMainAddress(false);
            }
        }
    }
    
    public Address getMainAddress(long personId){
        Person person = personRepository.findById(personId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Person with given Id does not exist"));
        
        for (Address address : person.getAddresses()) {
            if (address.getMainAddress()) {
                return address;
            }
        }

        throw new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Person does not have a main address");
    }
    // Util functions
    private void ValidateAddressesList(List<Address> addresses){
        int mainAddressExist = 0;

        for (Address address : addresses) {
            if (address.getMainAddress()) {
                mainAddressExist ++;
            }
        }
        if (mainAddressExist == 0) {
            throw new ResponseStatusException(
           HttpStatus.CONFLICT, "A person needs to have a main address");
        }
        if (mainAddressExist > 1) {
            throw new ResponseStatusException(
           HttpStatus.CONFLICT, "A person can only have one main address");
        }
    }
}
