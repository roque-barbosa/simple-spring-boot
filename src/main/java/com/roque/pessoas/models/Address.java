package com.roque.pessoas.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Address {
    
    @Id
    @SequenceGenerator(
        name = "address_sequence",
        allocationSize = 1,
        sequenceName = "address_sequence"
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_sequence")
    private long id;
    private boolean mainAddress;
    private String city;
    private String publicPlace;
    private String zipCode;
    private String number;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="person_id")
    private Person person;


    public Address(String city, String publicPlace, String zipCode, String number, Person person, boolean mainAddress) {
        this.city = city;
        this.publicPlace = publicPlace;
        this.zipCode = zipCode;
        this.number = number;
        this.person = person;
        this.mainAddress = mainAddress;
    }

    // Constructor without showing if is main address
    public Address(String city, String publicPlace, String zipCode, String number, Person person) {
        this.city = city;
        this.publicPlace = publicPlace;
        this.zipCode = zipCode;
        this.number = number;
        this.person = person;
    }

    public boolean getMainAddress(){
        return this.mainAddress;
    }

}
