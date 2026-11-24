import { TestBed } from '@angular/core/testing';
import { Autenticacionservice } from './autenticacionservice';

describe('Autenticacionservice', () => {
  let service: Autenticacionservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Autenticacionservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
