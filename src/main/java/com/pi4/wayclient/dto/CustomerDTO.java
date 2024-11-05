package com.pi4.wayclient.dto;

import com.pi4.wayclient.model.UserRole;

import java.util.List;
import java.util.UUID;

public record CustomerDTO(
        UUID id,
        String name,
        String email,
        UserRole role,
        String phone,
        List<TicketDTO> tickets,
        String username
) {
}
