package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
