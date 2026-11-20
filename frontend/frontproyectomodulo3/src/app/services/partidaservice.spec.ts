import { TestBed } from '@angular/core/testing';
import { Partidaservice } from './partidaservice';

describe('Partidaservice', () => {
  let service: Partidaservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Partidaservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
