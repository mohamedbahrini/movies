package com.example.movies.init;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.movies.models.Director;
import com.example.movies.models.Movie;
import com.example.movies.payload.MovieDto;
import com.example.movies.repositories.DirectorRepository;
import com.example.movies.repositories.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class InitDatabaseFromFile implements CommandLineRunner {
	private MovieRepository movieRepository;
	private DirectorRepository directorRepository;

	@Autowired
	public InitDatabaseFromFile(MovieRepository movieRepository, DirectorRepository directorRepository) {
		this.movieRepository = movieRepository;
		this.directorRepository = directorRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		// read json file data to String
		byte[] jsonData = Files.readAllBytes(Paths.get("C:\\Users\\moham\\Desktop\\assignment\\movies.json"));

		// create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();

		// convert json string to object
		MovieDto[] moviesDto = objectMapper.readValue(jsonData, MovieDto[].class);

		List<MovieDto> movieList = Arrays.asList(moviesDto);
		movieList.forEach(System.out::println);

		List<Movie> movies = movieList.stream().map(movieDto -> convertFromDto(movieDto)).collect(Collectors.toList());

		movies.forEach(movie -> movieRepository.save(movie));
	}

	private Movie convertFromDto(MovieDto movieDto) {
		Movie movie = new Movie();
		// create directors list
		String[] directorNameList = movieDto.getDirector().split(",");
		Set<Director> directors = new HashSet<>();
		for (int i = 0; i < directorNameList.length; i++) {
			String[] names = directorNameList[i].split(" ");
			Director director = new Director();
			director.setFirstname(names[0]);
			director.setLastname(names[1]);
			directors.add(director);
		}

		movie.setTitle(movieDto.getTitle());

		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			movie.setReleaseDate(formatter1.parse(movieDto.getReleaseDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		movie.setType(movieDto.getType());
		movie.setDirectors(directors);
		return movie;
	}

}
