package com.example.movies.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movies.models.Movie;
import com.example.movies.services.MoviesService;

@RestController
public class MovieController {
	private MoviesService moviesService;
	
	@Autowired
	public MovieController(MoviesService moviesService) {
		this.moviesService = moviesService;
	}

	@GetMapping("allMovies")
	public List<Movie> findAllMovies() {
		return moviesService.getAllMovies();
	}
}
