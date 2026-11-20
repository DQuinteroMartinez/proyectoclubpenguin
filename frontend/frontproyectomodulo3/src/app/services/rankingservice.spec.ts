import { TestBed } from '@angular/core/testing';
import { Rankingservice } from './rankingservice';

describe('Rankingservice', () => {
  let service: Rankingservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Rankingservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
