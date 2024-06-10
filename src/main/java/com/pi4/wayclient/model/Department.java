package com.pi4.wayclient.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @JsonManagedReference(value = "department-employee")
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    @ManyToMany(mappedBy = "departments")
    private List<Admin> admins;

    @JsonManagedReference(value = "department-ticket")
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

}
