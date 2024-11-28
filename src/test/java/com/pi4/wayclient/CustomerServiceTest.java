package com.pi4.wayclient;

import com.pi4.wayclient.dto.CustomerDTO;
import com.pi4.wayclient.model.Customer;
import com.pi4.wayclient.model.UserRole;
import com.pi4.wayclient.repository.CustomerRepository;
import com.pi4.wayclient.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        Customer customer = new Customer(
                "john.doe@example.com",
                "John Doe",
                "password123",
                UserRole.CUSTOMER,
                "123-456-7890"
        );

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(customer);

        assertNotNull(createdCustomer);
        assertEquals("John Doe", createdCustomer.getName());
        assertEquals("john.doe@example.com", createdCustomer.getEmail());
        assertEquals("123-456-7890", createdCustomer.getPhone());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testGetCustomerById() {
        UUID customerId = UUID.randomUUID();

        Customer mockCustomer = mock(Customer.class);
        when(mockCustomer.getId()).thenReturn(customerId);
        when(mockCustomer.getName()).thenReturn("John Doe");
        when(mockCustomer.getEmail()).thenReturn("john.doe@example.com");
        when(mockCustomer.getRole()).thenReturn(UserRole.CUSTOMER);
        when(mockCustomer.getPhone()).thenReturn("123-456-7890");

        when(mockCustomer.getTickets()).thenReturn(List.of());

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        Optional<CustomerDTO> foundCustomer = customerService.getCustomer(customerId);

        assertTrue(foundCustomer.isPresent());
        assertEquals("John Doe", foundCustomer.get().name());
        assertEquals("john.doe@example.com", foundCustomer.get().email());
        assertTrue(foundCustomer.get().tickets().isEmpty());

        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testUpdateCustomer() {
        UUID customerId = UUID.randomUUID();
        Customer existingCustomer = new Customer(
                customerId,
                "john.doe@example.com",
                "John Doe",
                "password123",
                UserRole.CUSTOMER,
                "123-456-7890"
        );

        Customer updatedCustomer = new Customer(
                customerId,
                "john.updated@example.com",
                "John Updated",
                "newpassword123",
                UserRole.CUSTOMER,
                "987-654-3210"
        );

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(existingCustomer);

        Customer result = customerService.updateCustomer(customerId, updatedCustomer);

        assertEquals("John Updated", result.getName());
        assertEquals("john.updated@example.com", result.getEmail());
        assertEquals("987-654-3210", result.getPhone());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testDeleteCustomer() {
        UUID customerId = UUID.randomUUID();

        doNothing().when(customerRepository).deleteById(customerId);

        customerService.deleteCustomer(customerId);

        verify(customerRepository, times(1)).deleteById(customerId);
    }

}
