package com.pi4.wayclient;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.pi4.wayclient.model.Ticket;
import com.pi4.wayclient.repository.CustomerRepository;
import com.pi4.wayclient.repository.DepartmentRepository;
import com.pi4.wayclient.repository.EmployeeRepository;
import com.pi4.wayclient.repository.TicketRepository;
import com.pi4.wayclient.service.TicketService;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class TicketServiceTest {

    private final TicketRepository ticketRepository = mock(TicketRepository.class);
    private final CustomerRepository customerRepository = mock(CustomerRepository.class);
    private final DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
    private final EmployeeRepository employeeRepository = mock(EmployeeRepository.class);

    private final TicketService ticketService = new TicketService(
            ticketRepository, customerRepository, departmentRepository, employeeRepository
    );

    @Test
    void testCreateTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(UUID.randomUUID());
        ticket.setStatus("Aberto");
        ticket.setDate(LocalDate.now());
        ticket.setTitle("Teste de Ticket");
        ticket.setDescription("Descrição do ticket de teste");

        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket createdTicket = ticketService.createTicket(ticket);

        assertNotNull(createdTicket);
        assertEquals("Teste de Ticket", createdTicket.getTitle());
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    void testUpdateTicketStatus() {
        UUID ticketId = UUID.randomUUID();
        Ticket existingTicket = new Ticket(ticketId, null, null, null, null, "Aberto", LocalDate.now(), "Descrição", "Título");
        Ticket updatedTicket = new Ticket();
        updatedTicket.setStatus("Fechado");

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(existingTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(existingTicket);

        Ticket result = ticketService.updateTicket(ticketId, updatedTicket);

        assertNotNull(result);
        assertEquals("Fechado", result.getStatus());
        verify(ticketRepository, times(1)).findById(ticketId);
        verify(ticketRepository, times(1)).save(existingTicket);
    }


}
