import { TestBed } from '@angular/core/testing';
import { Retoservice } from './retoservice';

describe('Retoservice', () => {
  let service: Retoservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Retoservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
