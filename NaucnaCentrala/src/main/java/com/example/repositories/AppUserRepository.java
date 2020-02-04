package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.Appuser;

@Repository
public interface AppUserRepository extends JpaRepository<Appuser, Long> {
	
	@Query("SELECT u FROM Appuser u WHERE u.email  = :email")
	Appuser findByEmail(@Param("email") String email);
	
	@Query("SELECT u FROM Appuser u WHERE u.username  = :username")
	Appuser findByUsername(@Param("username") String username);
}
