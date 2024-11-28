package com.pi4.wayclient;

import com.pi4.wayclient.model.Department;
import com.pi4.wayclient.model.Employee;
import com.pi4.wayclient.model.UserRole;
import com.pi4.wayclient.repository.EmployeeRepository;
import com.pi4.wayclient.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployee() {
        Department department = new Department(UUID.randomUUID(), "T.I");

        Employee employee = new Employee("john.doe@example.com", "John Doe", "password123", UserRole.EMPLOYEE, department);

        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee createdEmployee = employeeService.createEmployee(employee);

        assertEquals("John Doe", createdEmployee.getName());
        assertEquals("john.doe@example.com", createdEmployee.getEmail());
        assertEquals(department, createdEmployee.getDepartment());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testUpdateEmployee() {
        UUID employeeId = UUID.randomUUID();
        Department department = new Department(UUID.randomUUID(), "Recursos Humanos");
        Employee existingEmployee = new Employee("mary.smith@example.com", "Mary Smith", "oldPass123", UserRole.EMPLOYEE, department);

        Employee newEmployeeData = new Employee();
        newEmployeeData.setName("Mary Updated");
        newEmployeeData.setPassword("newPass456");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Employee updatedEmployee = employeeService.updateEmployee(employeeId, newEmployeeData);

        assertEquals("Mary Updated", updatedEmployee.getName());
        assertEquals("newPass456", updatedEmployee.getPassword());
        assertEquals("mary.smith@example.com", updatedEmployee.getEmail());
        verify(employeeRepository, times(1)).save(existingEmployee);
    }

    @Test
    void testDeleteEmployee() {
        // Mockando um UUID de funcionário
        UUID employeeId = UUID.randomUUID();

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

}