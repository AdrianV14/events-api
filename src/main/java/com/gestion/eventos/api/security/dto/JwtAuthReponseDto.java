package com.gestion.eventos.api.security.dto;

import lombok.Data;

@Data 
public class JwtAuthReponseDto {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthReponseDto(String accessToken){
        this.accessToken = accessToken;
    }
}
