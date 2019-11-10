import { Component, OnInit, OnDestroy } from '@angular/core';
import { Movie } from 'src/app/models/movie';
import { HttpClient } from '@angular/common/http';
import { HttpUtil } from 'src/app/models/http.util';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit, OnDestroy {
  movies: Movie[];
  movieObservable: Subscription;
  showEditing: boolean = false;
  editedMovie: Movie;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.movieObservable = this.http.get(HttpUtil.baseurl + 'allMovies').subscribe(
      (data: Movie[]) => {
        this.movies = data;
        console.log('movies', this.movies);
      },
      error => {
        console.log(error);
      }
    );
  }

  ngOnDestroy(): void {
    this.movieObservable.unsubscribe();
  }

  findEditedMovie(id: any): Movie {
    let i: number;
    for (i = 0; i < this.movies.length; i++) {
      if (this.movies[i].id == id) {
        console.log('edited', this.movies[i]);
        return this.movies[i];
      }
    }
    return null;
  }

  editMovie(event: any) {
    console.log('event', event);
    this.showEditing = true;
    window.scrollTo(0, 0);
    this.editedMovie = this.findEditedMovie(event);
  }


  saveMovie(movie: Movie) {
    console.log('movie', movie);
    if (movie.id) {
      // call update webservice and refresh
      this.http.put(HttpUtil.baseurl + 'movie', movie).subscribe(
        data => {
          this.ngOnInit();
          this.showEditing = false;
        },
        error => console.log(error)
      );
    } else {
      // call add webservice and refresh
      this.http.post(HttpUtil.baseurl + 'movie', movie).subscribe(
        data => {
          this.ngOnInit();
          this.showEditing = false;
        },
        error => console.log(error)
      );

    }
  }

  deleteMovie(movie: Movie) {
    const url = `${HttpUtil.baseurl}movie/${movie.id}`; 
    this.http.delete(url).subscribe(
      data => {
        this.ngOnInit();
        this.showEditing = false;
      },
      error => console.log(error)
    );
  }

  emptyForm() {
    this.editedMovie = {} as Movie;
    this.editedMovie.id = undefined;
    this.editedMovie.title = '';
    this.editedMovie.releaseDate = new Date();
    this.editedMovie.directors = [];
    this.editedMovie.type = '';
  }

  addMovie(){
    console.log('called');
    this.emptyForm();
    this.showEditing = true;
  }
}
