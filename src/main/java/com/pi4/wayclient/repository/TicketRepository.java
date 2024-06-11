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

    @Query("SELECT t FROM Ticket t LEFT JOIN FETCH t.customer")
    List<Ticket> findAllWithCustomer();

    @Query("SELECT t FROM Ticket t LEFT JOIN FETCH t.customer WHERE t.department.id = :departmentId")
    List<Ticket> findByDepartmentIdWithCustomer(UUID departmentId);
}
