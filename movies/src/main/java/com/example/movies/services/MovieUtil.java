package com.example.movies.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.movies.models.Director;
import com.example.movies.models.Movie;
import com.example.movies.payload.MovieDto;

public class MovieUtil {
	public static Movie convertFromDto(MovieDto movieDto) {
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

	public static MovieDto convertToDto(Movie movie) {
		MovieDto movieDto = new MovieDto();

		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		String releaseDate = dateFormat.format(movie.getReleaseDate());

		movieDto.setReleaseDate(releaseDate);

		// create directors
		List<String> directorsName = movie.getDirectors().stream()
				.map(director -> director.getFirstname() + " " + director.getLastname()).collect(Collectors.toList());
		
		movieDto.setDirector(String.join(", ", directorsName));
		movieDto.setTitle(movie.getTitle());
		movieDto.setType(movie.getType());
		
		return movieDto;
	}
}
