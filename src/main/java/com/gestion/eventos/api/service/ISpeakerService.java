package com.gestion.eventos.api.service;

import com.gestion.eventos.api.domain.Speaker;
import com.gestion.eventos.api.dto.SpeakerRequestDto;

import java.util.List;

public interface ISpeakerService {
    List<Speaker> findAll();

    Speaker findById(Long id);

    Speaker save(SpeakerRequestDto requestDto);

    Speaker update(Long id, SpeakerRequestDto requestDto);

    void deleteById(Long id);
}
