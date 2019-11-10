import { Component, OnInit, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { Movie } from 'src/app/models/movie';
import { Director } from 'src/app/models/director';

@Component({
  selector: 'app-movie-edit',
  templateUrl: './movie-edit.component.html',
  styleUrls: ['./movie-edit.component.css']
})
export class MovieEditComponent implements OnInit, OnChanges {

  @Input() movie: Movie;
  @Output() saveMovie: EventEmitter<any> = new EventEmitter();
  editedMovie: Movie;
  showDirectorForm: boolean = false;
  firstname: string;
  lastname: string;

  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(changes: import("@angular/core").SimpleChanges): void {
    if (this.movie)
      this.editedMovie = JSON.parse(JSON.stringify(this.movie));
  }


  emptyForm() {
    this.editedMovie.id = undefined;
    this.editedMovie.title = undefined;
    this.editedMovie.releaseDate = undefined;
    this.editedMovie.directors = undefined;
    this.editedMovie.type = undefined;
  }

  saveChanges() {
    this.movie = JSON.parse(JSON.stringify(this.editedMovie));
    this.saveMovie.emit(this.movie);
    this.emptyForm();
  }

  addDirector() {
    this.showDirectorForm = true;
  }

  saveDirector() {
    let director = {} as Director;
    director.firstname = this.firstname;
    director.lastname = this.lastname;

    this.editedMovie.directors.push(director);
    this.showDirectorForm = false;
  }
}
