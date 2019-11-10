import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieElementComponent } from './movie-element.component';

describe('MovieElementComponent', () => {
  let component: MovieElementComponent;
  let fixture: ComponentFixture<MovieElementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MovieElementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
