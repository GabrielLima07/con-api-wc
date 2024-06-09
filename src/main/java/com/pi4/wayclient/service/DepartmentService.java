package com.pi4.wayclient.service;

import com.pi4.wayclient.model.Department;
import com.pi4.wayclient.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }
    public Department createDepartment(Department department){
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartment(){
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartment(UUID id){
        return departmentRepository.findById(id);
    }

    public Department getDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }

    public Department updateDepartment(Department department){
        return departmentRepository.save(department);
    }

    public void deleteDepartment(UUID id){
        departmentRepository.deleteById(id);
    }

}
