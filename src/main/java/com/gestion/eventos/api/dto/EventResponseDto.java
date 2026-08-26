package com.gestion.eventos.api.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EventResponseDto {
    private Long id;
    private String name;
    private LocalDate data;
    private String location;
}
