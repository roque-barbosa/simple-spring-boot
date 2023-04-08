package com.roque.pessoas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roque.pessoas.models.Address;

public interface IAddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByPersonId(long person_id);
}
