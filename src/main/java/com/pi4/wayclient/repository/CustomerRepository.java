package com.pi4.wayclient.repository;


import com.pi4.wayclient.model.Customer;
import com.pi4.wayclient.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);
    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) = LOWER(:name)")
    List<Customer> findByName(@Param("name") String name);





}
