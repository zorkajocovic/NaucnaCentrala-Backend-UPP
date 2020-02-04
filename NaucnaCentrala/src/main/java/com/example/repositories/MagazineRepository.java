package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Magazine;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Long> {
}
