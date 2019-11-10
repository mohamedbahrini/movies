package com.example.movies.services;

import java.util.List;

import com.example.movies.models.Movie;

public interface MoviesService {
	List<Movie> getAllMovies();
	Movie saveMovie(Movie movie);
	void updateMovie(Movie movie);
	void deleteMovie(Long id);
	List<Movie> searchMovies(String search, String sort);
}
