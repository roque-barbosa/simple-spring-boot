package com.roque.pessoas.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roque.pessoas.models.Address;
import com.roque.pessoas.services.AddressService;


@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {
    
    private final AddressService addressService;
    
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/")
    public List<Address> index() {
        return addressService.getAllAddresses();
    }

    @PutMapping("/{addressId}")
    public void update(@PathVariable("addressId") long addressId, @RequestBody Address address){
        addressService.updateAddress(addressId, address);
    }
}
