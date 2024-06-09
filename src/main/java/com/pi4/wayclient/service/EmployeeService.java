package com.pi4.wayclient.service;

import com.pi4.wayclient.model.Department;
import com.pi4.wayclient.model.Employee;
import com.pi4.wayclient.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployee(UUID id) {
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(UUID id, Employee newEmployee) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    if (newEmployee.getName() != null) {
                        employee.setName(newEmployee.getName());
                    }
                    if (newEmployee.getEmail() != null) {
                        employee.setEmail(newEmployee.getEmail());
                    }
                    if (newEmployee.getPassword() != null) {
                        employee.setPassword(newEmployee.getPassword());
                    }
                    if (newEmployee.getDepartment() != null) {
                        employee.setDepartment(newEmployee.getDepartment());
                    }
                    if (newEmployee.getPosition() != null) {
                        employee.setPosition(newEmployee.getPosition());
                    }
                    return employeeRepository.save(employee);
                })
                .orElseGet(() -> employeeRepository.save(newEmployee));
    }


    public void deleteEmployee(UUID id) {
        employeeRepository.deleteById(id);
    }

    public Department getEmployeeDepartment(UUID employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return employee.getDepartment();
    }

    public Department getEmployeeDepartmentByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return employee.getDepartment();
    }
}
