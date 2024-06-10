package com.pi4.wayclient.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Employee extends User {
    @ManyToOne
    @JsonBackReference(value = "department-employee")
    @JoinColumn(name = "department_id")
    private Department department;
    @Column
    private String position;

    public Employee(String email, String name, String password, UserRole role, Department department) {
        super(email, name, password, role);
        this.department = department;
    }
}