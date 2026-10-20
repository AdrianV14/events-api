package com.gestion.eventos.api.service;

import com.gestion.eventos.api.domain.Speaker;
import com.gestion.eventos.api.dto.SpeakerRequestDto;
import com.gestion.eventos.api.exception.ResourceNotFoundException;
import com.gestion.eventos.api.mapper.SpeakerMapper;
import com.gestion.eventos.api.repository.ISpeakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeakerService implements ISpeakerService {
    private final ISpeakerRepository speakerRepository;
    private final SpeakerMapper speakerMapper;


    @Override
    @Transactional(readOnly = true)
    public List<Speaker> findAll() {
        return speakerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Speaker findById(Long id) {
        return speakerRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No se encontro un expositor con el Id: " + id));
    }

    @Override
    @Transactional
    public Speaker save(SpeakerRequestDto requestDto) {
        Speaker speaker = speakerMapper.toEntity(requestDto);
        return speakerRepository.save(speaker);
    }

    @Override
    @Transactional
    public Speaker update(Long id, SpeakerRequestDto requestDto) {
        Speaker existingSpeaker = speakerRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No se encontro un expositor con el Id: " + id));
        speakerMapper.updateSpeakerFromDto(requestDto, existingSpeaker);
        return speakerRepository.save(existingSpeaker);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!speakerRepository.existsById(id)){
            throw new ResourceNotFoundException("No se encontro un expositor con el Id: " + id);
        }

        speakerRepository.deleteById(id);
    }
}
