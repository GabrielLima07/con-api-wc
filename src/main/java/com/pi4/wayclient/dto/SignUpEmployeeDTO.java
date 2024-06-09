package com.pi4.wayclient.dto;

import com.pi4.wayclient.model.Department;
import com.pi4.wayclient.model.UserRole;

public record SignUpEmployeeDTO(String email, String name, String password, UserRole role, String departmentName) {
}
