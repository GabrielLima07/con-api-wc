package com.pi4.wayclient.controller;

import com.pi4.wayclient.model.Customer;
import com.pi4.wayclient.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer postCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping
    public List<Customer> getCustomer() {
        return customerService.getAllCustomer();
    }

    @GetMapping("/{id}")
    public Optional<Customer> getCustomer(@PathVariable UUID id) { return customerService.getCustomer(id);
    }

    @GetMapping("/name/{name}")
    public List<Customer> getCustomerByName(@PathVariable String name) {
        System.out.println("Buscando cliente com nome: " + name);
        return customerService.getCustomerName(name);
    }

    @PutMapping("/{id}")
    public Customer putCustomer(@PathVariable UUID id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable UUID id) {
        try {
           customerService.deleteCustomer(id);
            return "Customer with ID " + id + " deleted !";
        } catch (Exception e) {
            return "Failed to delete customer with ID " + id + ": " + e.getMessage();
        }
    }


}
