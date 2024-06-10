package com.pi4.wayclient.service;

import com.pi4.wayclient.model.Department;
import com.pi4.wayclient.model.Ticket;
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

    //TODO: Refatorar - alterar função p/ seguir padrão de update dos outros services
    public Department updateDepartment(Department department){
        return departmentRepository.save(department);
    }

    public void deleteDepartment(UUID id){
        departmentRepository.deleteById(id);
    }

    public List<Ticket> getDepartmentTickets(UUID id) {
        Optional<Department> department = departmentRepository.findById(id);

        if(department.isPresent()) {
            return department.get().getTickets();
        } else {
            return null;
        }
    }
}
