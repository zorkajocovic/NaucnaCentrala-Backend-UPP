package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
	
	@Query("SELECT u FROM Subscription u WHERE u.appuser  = :appuser and u.magazine = :magazine")
	Subscription findByUserAndMagazine(@Param("appuser") Long appuser, @Param("magazine") Long magazine);	
}
