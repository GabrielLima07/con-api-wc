package com.pi4.wayclient.dto;

import java.util.UUID;

public record LoginResponseDTO(String token, String userType, UUID userId) {
}
