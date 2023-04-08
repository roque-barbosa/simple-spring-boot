package com.roque.pessoas.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table
@Getter @Setter @AllArgsConstructor @NoArgsConstructor

public class Person {
    
    @Id
    @SequenceGenerator(
        name = "person_sequence",
        allocationSize = 1,
        sequenceName = "person_sequence"
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_sequence")
    private long id;
    private String name;
    // private String address;
    private String phone;
    private String genre;
    private String email;
    private LocalDate birthDate;

    @JsonManagedReference
    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private List<Address> addresses;


    public Person(String name, List<Address> addresses, String phone, String genre, String email, LocalDate birthDate) {
        this.name = name;
        this.addresses = addresses;
        this.phone = phone;
        this.genre = genre;
        this.email = email;
        this.birthDate = birthDate;
    }

}
