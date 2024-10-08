package com.pi4.wayclient.dto;

import com.pi4.wayclient.model.Product;
import com.pi4.wayclient.model.ServiceEntity;

import java.time.LocalDate;
import java.util.UUID;

public record TicketDTO(UUID id, String status, LocalDate date, String description, String title, String customerName, String departmentName, String employeeName, Product product, ServiceEntity service) {
}
