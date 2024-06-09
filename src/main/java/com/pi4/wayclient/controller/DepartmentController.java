package com.pi4.wayclient.controller;

import com.pi4.wayclient.model.Department;
import com.pi4.wayclient.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }
    @PostMapping
    public Department postDepartament(@RequestBody Department department){
        return departmentService.createDepartment(department);
    }

    @GetMapping
    public List<Department> getDepartment(){return  departmentService.getAllDepartment();}

    @GetMapping("/{id}")
    public Optional<Department> getDepartment(@PathVariable UUID id){
        return departmentService.getDepartment(id);
    }

    @GetMapping("/{name}")
    public Department getDepartmentByName(@PathVariable String name) {
        return departmentService.getDepartmentByName(name);
    }

    @PutMapping("/{id}")
    public Department putDepartment(@PathVariable UUID id, @RequestBody Department department){
        department.setId(id);
        return departmentService.updateDepartment(department);
    }

    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable UUID id){
        try{
            departmentService.deleteDepartment(id);
            return "Department with ID " + id + " deleted successfully";
        }catch (Exception e){
            return "Failed to delete department with ID" + id + ": " + e.getMessage();
        }
    }
}
