package com.pi4.wayclient.service;

import com.pi4.wayclient.model.Customer;
import com.pi4.wayclient.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CustomerService {

    private  final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomer(UUID id) {
        return customerRepository.findById(id);
    }
    public List<Customer> getCustomerName(String name) {
        return customerRepository.findByName(name);
    }

    public Customer updateCustomer(UUID id, Customer newCustomer) {
        return customerRepository.findById(id)
                .map(customer -> {
                    if (newCustomer.getName() != null) {
                        customer.setName(newCustomer.getName());
                    }
                    if (newCustomer.getEmail() != null) {
                        customer.setEmail(newCustomer.getEmail());
                    }
                    if (newCustomer.getPassword() != null) {
                        customer.setPassword(newCustomer.getPassword());
                    }
                    if (newCustomer.getPhone() != null) {
                        customer.setPhone(newCustomer.getPhone());
                    }
                    if (newCustomer.getTickets() != null) {
                        customer.setTickets(newCustomer.getTickets());
                    }
                    return customerRepository.save(customer);
                })
                .orElseGet(() -> customerRepository.save(newCustomer));
    }


    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }
}
