package com.pi4.wayclient.controller;

import com.pi4.wayclient.dto.TicketDTO;
import com.pi4.wayclient.model.Customer;
import com.pi4.wayclient.model.Ticket;
import com.pi4.wayclient.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("ticket")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<Ticket> postTicket(@RequestBody Ticket ticket) {
        Ticket newTicket = ticketService.createTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTicket);
    }


    @GetMapping
    public ResponseEntity<List<TicketDTO>> getTicket() {
        List<TicketDTO> ticketList = ticketService.retrieveTickets();
        return ResponseEntity.status(HttpStatus.OK).body(ticketList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Ticket>> getTicket(@PathVariable UUID id) {
        Optional<Ticket> ticket = ticketService.retrieveTicketById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> putTicket(@PathVariable UUID id, @RequestBody Ticket ticket) {
        Ticket updatedTicket = ticketService.updateTicket(id, ticket);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {
        boolean deleted = ticketService.deleteTicket(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{ticketId}/customer")
    public Customer getCustomerByTicketId(@PathVariable UUID ticketId) {
        return ticketService.getCustomerByTicketId(ticketId);
    }

    @GetMapping("/search/{customerId}")
    public ResponseEntity<List<Ticket>> searchTicketsByTitle(
            @PathVariable UUID customerId,
            @RequestParam String title) {

        // Busca os tickets do cliente com base no título
        List<Ticket> tickets = ticketService.findTicketsByTitleAndCustomer(title, customerId);

        if (tickets.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 se não encontrar tickets
        }

        return ResponseEntity.ok(tickets); // Retorna 200 com os tickets encontrados
    }

}
