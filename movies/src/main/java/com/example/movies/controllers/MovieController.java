package com.example.movies.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.movies.models.Movie;
import com.example.movies.services.MoviesService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

	@PostMapping("/movie")
	public Movie saveMovie(@RequestBody Movie movie) {
		return moviesService.saveMovie(movie);
	}

	@PutMapping("/movie")
	public void updateMovie(@RequestBody Movie movie) {
		System.out.println("movie" + movie);
		moviesService.updateMovie(movie);
	}

	@DeleteMapping("/movie/{id}")
	public void deleteMovie(@PathVariable Long id) {
		moviesService.deleteMovie(id);
	}
	
	@GetMapping("/search")
	public List<Movie> searchMovie(@RequestParam String search, @RequestParam String sort){
		return moviesService.searchMovies(search, sort);
	}
}
