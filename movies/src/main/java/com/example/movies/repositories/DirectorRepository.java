package com.example.movies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movies.models.Director;

public interface DirectorRepository extends JpaRepository<Director, Long> {
	boolean existsByFirstnameAndLastname(String firstname, String lastname);
	Director findByFirstnameAndLastname(String firstname, String lastname);
}
