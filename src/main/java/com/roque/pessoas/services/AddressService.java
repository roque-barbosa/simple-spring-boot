package com.roque.pessoas.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.roque.pessoas.models.Address;
import com.roque.pessoas.repositories.IAddressRepository;

import jakarta.transaction.Transactional;

@Service
public class AddressService {
    private final IAddressRepository addressRepository;

    public AddressService(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public List<Address> getAllAddressesByPersonId(long personId) {
        return addressRepository.findByPersonId(personId);
    }

    @Transactional
    public void updateAddress(long addressId, Address address){
        Address addressToUpdate = addressRepository.findById(addressId)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Address with given Id does not exist"));
        
        addressToUpdate.setZipCode(address.getZipCode());
        addressToUpdate.setCity(address.getCity());
        addressToUpdate.setNumber(address.getNumber());
        addressToUpdate.setPublicPlace(address.getPublicPlace());
    }
    
}
