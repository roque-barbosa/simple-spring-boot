package com.roque.pessoas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roque.pessoas.models.Person;

public interface IPersonRepository extends JpaRepository<Person, Long> {
    
    Optional<Person> findByEmail(String email);
}
