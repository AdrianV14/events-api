package com.gestion.eventos.api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.gestion.eventos.api.domain.Event;
import com.gestion.eventos.api.dto.EventResponseDto;

@Mapper(componentModel = "spring")
public interface IEventMapper {
    List<EventResponseDto> toEventResponseDtosList(List<Event> events);
}
