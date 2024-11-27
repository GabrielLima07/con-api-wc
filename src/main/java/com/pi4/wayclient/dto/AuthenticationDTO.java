package com.pi4.wayclient.dto;

public record AuthenticationDTO(String email, String password, String recaptchaToken) {

}
