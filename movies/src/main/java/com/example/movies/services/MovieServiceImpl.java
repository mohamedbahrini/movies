package com.example.movies.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movies.models.Movie;
import com.example.movies.repositories.MovieRepository;

@Service
public class MovieServiceImpl implements MoviesService {
	private MovieRepository movieRepository;

	@Autowired
	public MovieServiceImpl(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

}
