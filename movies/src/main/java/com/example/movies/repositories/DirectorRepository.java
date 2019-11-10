package com.example.movies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movies.models.Director;

public interface DirectorRepository extends JpaRepository<Director, Long> {

}
