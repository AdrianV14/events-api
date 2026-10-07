package com.gestion.eventos.api.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class EventSummaryDto {
    private Long id;
    private String name;
    private LocalDate date;
    private String location; 
}
