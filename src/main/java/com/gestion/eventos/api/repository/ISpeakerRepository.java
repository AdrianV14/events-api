package com.gestion.eventos.api.repository;

import com.gestion.eventos.api.domain.Speaker;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository 
public interface ISpeakerRepository extends JpaRepository<Speaker, Long>{
    Optional<Speaker> findByEmail(String email);
    Boolean existsByEmail(String name);
}
