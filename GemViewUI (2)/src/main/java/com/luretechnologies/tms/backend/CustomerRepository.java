package com.luretechnologies.tms.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luretechnologies.tms.backend.data.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
