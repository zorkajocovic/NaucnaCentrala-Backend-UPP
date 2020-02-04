package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.ScientificField;

@Repository
public interface ScientificFieldRepository extends JpaRepository<ScientificField, Long> {
}