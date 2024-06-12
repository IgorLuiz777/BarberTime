package br.com.fiap.barbertime.model.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
    
}
