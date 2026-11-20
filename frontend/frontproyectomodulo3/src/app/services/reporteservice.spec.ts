import { TestBed } from '@angular/core/testing';
import { Reporteservice } from './reporteservice';

describe('Reporteservice', () => {
  let service: Reporteservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Reporteservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
