package com.pi4.wayclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.pi4.wayclient.model.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    @Query("SELECT a FROM Department a WHERE a.name = :name")
    Department findByName(@Param("name") String name);
}
