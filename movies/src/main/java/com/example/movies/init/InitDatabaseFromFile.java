package com.example.movies.init;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.movies.models.Movie;
import com.example.movies.payload.MovieDto;
import com.example.movies.repositories.MovieRepository;
import com.example.movies.services.MovieUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class InitDatabaseFromFile implements CommandLineRunner {
	private MovieRepository movieRepository;

	@Value("${file.path}")
	String path;
	
	@Autowired
	public InitDatabaseFromFile(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		// read json file data to String
		byte[] jsonData = Files.readAllBytes(Paths.get(path));

		// create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();

		// convert json string to object
		MovieDto[] moviesDto = objectMapper.readValue(jsonData, MovieDto[].class);

		List<MovieDto> movieList = Arrays.asList(moviesDto);
		movieList.forEach(System.out::println);

		List<Movie> movies = movieList.stream().map(movieDto -> MovieUtil.convertFromDto(movieDto))
				.collect(Collectors.toList());

		movies.forEach(movie -> movieRepository.save(movie));
	}

}
