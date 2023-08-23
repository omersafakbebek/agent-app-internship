package com.example.softwareTracker.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String accessToken;

    public LoginResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
