package com.pi4.wayclient.service;

import com.pi4.wayclient.dto.CustomerDTO;
import com.pi4.wayclient.dto.TicketDTO;
import com.pi4.wayclient.model.Customer;
import com.pi4.wayclient.repository.CustomerRepository;
import com.pi4.wayclient.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


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

    public Optional<CustomerDTO> getCustomer(UUID id) {
        return customerRepository.findById(id)
                .map(this::convertToCustomerDTO);
    }
    public List<Customer> getCustomerName(String name) {
        return customerRepository.findByName(name);
    }

    private CustomerDTO convertToCustomerDTO(Customer customer) {
        List<TicketDTO> ticketDTOs = customer.getTickets().stream()
                .map(ticket -> new TicketDTO(
                        ticket.getId(),
                        ticket.getStatus(),
                        ticket.getDate(),
                        ticket.getDescription(),
                        ticket.getTitle(),
                        customer.getName(),
                        ticket.getDepartment() != null ? ticket.getDepartment().getName() : "Sem departamento",
                        ticket.getEmployee() != null ? ticket.getEmployee().getName() : "Nenhum",
                        ticket.getProduct(),
                        ticket.getService()
                ))
                .collect(Collectors.toList());

        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getRole(),
                customer.getPhone(),
                ticketDTOs,
                customer.getUsername()
        );
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
