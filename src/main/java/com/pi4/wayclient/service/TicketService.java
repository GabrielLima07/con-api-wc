package com.pi4.wayclient.service;

import com.pi4.wayclient.dto.TicketDTO;
import com.pi4.wayclient.model.Customer;
import com.pi4.wayclient.model.Department;
import com.pi4.wayclient.model.Employee;
import com.pi4.wayclient.model.Ticket;
import com.pi4.wayclient.repository.CustomerRepository;
import com.pi4.wayclient.repository.DepartmentRepository;
import com.pi4.wayclient.repository.EmployeeRepository;
import com.pi4.wayclient.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, CustomerRepository customerRepository, DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<TicketDTO> retrieveTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        List<TicketDTO> ticketDTOs = new ArrayList<>();
        String employeeName = "Nenhum";
        String departmentName = "Sem departamento";

        for (Ticket ticket : tickets) {
            Optional<Customer> customer = customerRepository.findById(ticket.getCustomer().getId());

            if (ticket.getDepartment() != null) {
                Optional<Department> department = departmentRepository.findById(ticket.getDepartment().getId());
                if (department.isPresent()) {
                    departmentName = department.get().getName();
                }
            } else {
                departmentName = "Sem departamento";
            }

            if (ticket.getEmployee() != null) {
                Optional<Employee> employee = employeeRepository.findById(ticket.getEmployee().getId());
                if (employee.isPresent()) {
                    employeeName = employee.get().getName();
                }
            } else {
                employeeName = "Nenhum";
            }

            TicketDTO dto = new TicketDTO(
                    ticket.getId(),
                    ticket.getStatus(),
                    ticket.getDate(),
                    ticket.getDescription(),
                    ticket.getTitle(),
                    (customer.isPresent() ? customer.get().getName() : "Desconhecido"),
                    departmentName,
                    employeeName,
                    ticket.getProduct(),
                    ticket.getService()
            );

            ticketDTOs.add(dto);
        }

        return ticketDTOs;
    }

    public Optional<Ticket> retrieveTicketById(UUID id) {
        return ticketRepository.findById(id);
    }

    public Ticket updateTicket(UUID id, Ticket updatedTicket) {
        return ticketRepository.findById(id)
                .map(ticket -> {
                    ticket.setDepartment((updatedTicket.getDepartment() == null) ? ticket.getDepartment() : updatedTicket.getDepartment());
                    ticket.setProduct((updatedTicket.getProduct() == null) ? ticket.getProduct() : updatedTicket.getProduct());
                    ticket.setService((updatedTicket.getService() == null) ? ticket.getService() : updatedTicket.getService());
                    ticket.setStatus((updatedTicket.getStatus() == null) ? ticket.getStatus() : updatedTicket.getStatus());
                    ticket.setDescription((updatedTicket.getDescription() == null) ? ticket.getDescription() : updatedTicket.getDescription());
                    ticket.setEmployee((updatedTicket.getEmployee() == null) ? ticket.getEmployee() : updatedTicket.getEmployee());
                    ticket.setTitle((updatedTicket.getTitle() == null) ? ticket.getTitle() : updatedTicket.getTitle());
                    //ticket.setDate((updatedTicket.getDate() == null) ? ticket.getDate() : updatedTicket.getDate());
                    return ticketRepository.save(ticket);
                })
                .orElseGet(() -> ticketRepository.save(updatedTicket));
    }

    public boolean deleteTicket(UUID id) {
        try {
            ticketRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Customer getCustomerByTicketId(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        return ticket.getCustomer();
    }
}
