import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Movie } from 'src/app/models/movie';

@Component({
  selector: 'app-movie-element',
  templateUrl: './movie-element.component.html',
  styleUrls: ['./movie-element.component.css']
})
export class MovieElementComponent implements OnInit {
  @Input() movie: Movie;
  @Output() edit: EventEmitter<any> = new EventEmitter();
  @Output() delete: EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  editMovie(id: number) {
    this.edit.emit(id);
  }

  deleteMovie(id: number) {
    this.delete.emit(id);
  }

}
