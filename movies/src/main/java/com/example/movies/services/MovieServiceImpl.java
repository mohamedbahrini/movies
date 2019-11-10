package com.example.movies.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.movies.models.Movie;
import com.example.movies.payload.MovieDto;
import com.example.movies.repositories.MovieRepository;
import com.example.movies.repositories.MovieSpecificationBuilder;
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
		List<MovieDto> movieDtos = movies.stream().map(movie -> MovieUtil.convertToDto(movie))
				.collect(Collectors.toList());
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		try {
			writer.writeValue(new File(path), movieDtos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Movie> searchMovies(String search, String sort) {
		MovieSpecificationBuilder builder = new MovieSpecificationBuilder();
		Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
		Matcher matcher = pattern.matcher(search + ",");
		while (matcher.find()) {
			builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
		}

		Direction direction = null;
		String sortedColumn = null;
		String[] sorting = sort.split(":");
		if (sorting.length == 2) {
			sortedColumn = sorting[0];
			String order = sorting[1];

			if (order.equals("asc")) {
				direction = Sort.Direction.ASC;
			} else {
				direction = Sort.Direction.DESC;
			}
		}

		Specification<Movie> spec = builder.build();

		return movieRepository.findAll(spec, Sort.by(direction, sortedColumn));
	}

}
