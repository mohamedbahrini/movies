package com.example.movies.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.movies.models.Movie;
import com.example.movies.payload.MovieDto;
import com.example.movies.repositories.MovieRepository;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
public class MovieServiceImpl implements MoviesService {
	private MovieRepository movieRepository;

	@Value("${file.path}")
	String path;
	
	@Autowired
	public MovieServiceImpl(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	@Override
	public Movie saveMovie(Movie movie) {
		Movie savedMovie = movieRepository.save(movie);
		saveChangesToFile();
		return savedMovie;
	}

	@Override
	public void updateMovie(Movie movie) {
		movieRepository.save(movie);
		saveChangesToFile();
	}

	@Override
	public void deleteMovie(Long id) {
		movieRepository.deleteById(id);
		saveChangesToFile();
	}
	
	public void saveChangesToFile() {
		List<Movie> movies = getAllMovies();
		List<MovieDto> movieDtos = movies.stream().map(movie -> MovieUtil.convertToDto(movie)).collect(Collectors.toList());
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		try {
			writer.writeValue(new File(path), movieDtos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
