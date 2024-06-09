package com.pi4.wayclient.repository;

import com.pi4.wayclient.model.Admin;
import com.pi4.wayclient.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByEmail(String email);
}
