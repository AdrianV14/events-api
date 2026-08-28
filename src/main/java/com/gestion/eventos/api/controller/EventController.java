package com.gestion.eventos.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.eventos.api.domain.Event;
import com.gestion.eventos.api.dto.EventRequestDto;
import com.gestion.eventos.api.dto.EventResponseDto;
import com.gestion.eventos.api.mapper.IEventMapper;
import com.gestion.eventos.api.service.IEventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final IEventService eventService;
    private final IEventMapper eventMapper;

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        List<Event> events = eventService.findAll();
        List<EventResponseDto> responseDtos = eventMapper.toEventResponseDtosList(events);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable Long id) {
        Event event = eventService.findById(id);
        EventResponseDto responseDto = eventMapper.toResponseDto(event);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@Valid @RequestBody EventRequestDto requestDto) {
        Event eventToSave = eventMapper.toEntity(requestDto);
        Event eventSaved = eventService.save(eventToSave);
        EventResponseDto responseDto = eventMapper.toResponseDto(eventSaved);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable Long id,
            @Valid @RequestBody EventRequestDto requestDto) {

        Event eventToUpdate = eventService.findById(id);
        eventMapper.updateEventFromDto(requestDto, eventToUpdate);
        Event updateEvent = eventService.save(eventToUpdate);
        EventResponseDto responseDto = eventMapper.toResponseDto(updateEvent);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
