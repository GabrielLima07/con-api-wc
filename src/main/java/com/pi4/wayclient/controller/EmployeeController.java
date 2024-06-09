package com.pi4.wayclient.controller;

import com.pi4.wayclient.model.Department;
import com.pi4.wayclient.model.Employee;
import com.pi4.wayclient.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public Employee postEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @GetMapping
    public List<Employee> getEmployee() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Optional<Employee> getEmployee(@PathVariable UUID id) {
        return employeeService.getEmployee(id);
    }

    @PutMapping("/{id}")
    public Employee putEmployee(@PathVariable UUID id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable UUID id) {
        try {
            employeeService.deleteEmployee(id);
            return "Employee with ID " + id + " deleted successfully";
        } catch (Exception e) {
            return "Failed to delete employee with ID " + id + ": " + e.getMessage();
        }
    }

    @GetMapping("/{id}/department")
    public Department getDepartmentByEmployeeId(@PathVariable UUID id) {
        return employeeService.getEmployeeDepartment(id);
    }

    @GetMapping("/email/{email}/department")
    public Department getDepartmentByEmployeeEmail(@PathVariable String email) {
        return employeeService.getEmployeeDepartmentByEmail(email);
    }
}
