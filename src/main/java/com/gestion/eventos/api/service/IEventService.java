package com.gestion.eventos.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gestion.eventos.api.domain.Event;


public interface IEventService {
    List<Event> findAll();
}
