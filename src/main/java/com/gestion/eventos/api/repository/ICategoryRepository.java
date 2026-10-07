package com.gestion.eventos.api.repository;

import com.gestion.eventos.api.domain.Category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository 
public interface ICategoryRepository extends JpaRepository<Category, Long>{
    Optional<Category> findByName(String name);
    Boolean existsByName(String name);
}
