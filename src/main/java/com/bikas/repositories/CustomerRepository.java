package com.bikas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikas.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
}
