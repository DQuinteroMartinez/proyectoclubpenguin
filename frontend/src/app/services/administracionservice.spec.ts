import { TestBed } from '@angular/core/testing';
import { Administracionservice } from './administracionservice';

describe('Administracionservice', () => {
  let service: Administracionservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Administracionservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
