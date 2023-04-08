package com.roque.pessoas.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roque.pessoas.dto.requests.ChangeMainAddressRequestBody;
import com.roque.pessoas.models.Address;
import com.roque.pessoas.models.Person;
import com.roque.pessoas.services.AddressService;
import com.roque.pessoas.services.PersonService;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {
    
    private final PersonService personService;
    private final AddressService addressService;
    
    public PersonController(PersonService personService, AddressService addressService) {
        this.personService = personService;
        this.addressService = addressService;
    }

    @GetMapping("/")
    public List<Person> index() {
        return personService.getAllPersons();
    }

    @PostMapping("/")
    public void register(@RequestBody Person person){
        personService.addNewPerson(person);
    }

    @GetMapping("/{personId}")
    public Person getPersonById(@PathVariable("personId") long personId) {
        return personService.getPersonById(personId);
    }

    @GetMapping("/{personId}/addresses")
    public List<Address> getByPerson(@PathVariable("personId") long personId) {
        return addressService.getAllAddressesByPersonId(personId);
    }

    @PostMapping("/{personId}/changeMainAddress")
    public void changeMainAddress(@PathVariable("personId") long personId, @RequestBody ChangeMainAddressRequestBody requestBody){
        personService.changeMainAddress(personId, requestBody.getAddressId());
    }

    @GetMapping("/{personId}/mainAddress")
    public Address getMainAddress(@PathVariable("personId") long personId){
        return personService.getMainAddress(personId);
    }

    @DeleteMapping("/{personId}")
    public void delete(@PathVariable("personId") long personId){
        personService.deletePerson(personId);
    }

    @PutMapping("/{personId}")
    public void update(@PathVariable("personId") long personId, @RequestBody Person person){
        personService.updatePerson(personId, person);
    }
}
