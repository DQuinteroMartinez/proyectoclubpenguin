import { TestBed } from '@angular/core/testing';
import { Minijuegoservice } from './minijuegoservice';

describe('Minijuegoservice', () => {
  let service: Minijuegoservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Minijuegoservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
