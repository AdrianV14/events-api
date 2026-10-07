package com.gestion.eventos.api.mapper;


import com.gestion.eventos.api.dto.SpeakerDto;
import com.gestion.eventos.api.domain.Speaker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpeakerMapper {
    SpeakerDto toDto(Speaker speaker);
    Speaker toEntity(SpeakerDto speakerDto);

}
