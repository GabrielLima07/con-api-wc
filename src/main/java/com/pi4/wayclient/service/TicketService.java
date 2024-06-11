package com.pi4.wayclient.service;

import com.pi4.wayclient.model.Customer;
import com.pi4.wayclient.model.Ticket;
import com.pi4.wayclient.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> retrieveTickets() {
        return ticketRepository.findAll();
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
