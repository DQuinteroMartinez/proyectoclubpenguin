import { TestBed } from '@angular/core/testing';
import { Posicionservice } from './posicionservice';

describe('Posicionservice', () => {
  let service: Posicionservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Posicionservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
