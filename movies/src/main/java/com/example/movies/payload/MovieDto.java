package com.example.movies.payload;

public class MovieDto {
	private String title;
	private String director;
	private String releaseDate;
	private String type;
	
	public MovieDto() {
	}
	public MovieDto(String title, String director, String releaseDate, String type) {
		this.title = title;
		this.director = director;
		this.releaseDate = releaseDate;
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "MovieDto [title=" + title + ", director=" + director + ", releaseDate=" + releaseDate + ", type=" + type
				+ "]";
	}
	
	
}
