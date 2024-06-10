package com.pi4.wayclient.repository;

import com.pi4.wayclient.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByCustomer_Id(UUID customerId);

    @Query("SELECT t FROM Ticket t WHERE t.customer.email = :email")
    List<Ticket> findByCustomerEmail(@Param("email") String email);

//    @Query(value = "SELECT * FROM Ticket t JOIN TicketDetail td ON t.ticket_detail_id = td.id WHERE td.status = :status", nativeQuery = true)
//    List<Ticket> findByTicketStatus(@Param("status") String status);
}
