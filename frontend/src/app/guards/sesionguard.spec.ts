import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';
import { sesionguard } from './sesionguard';

describe('sesionguard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) =>
      TestBed.runInInjectionContext(() => sesionguard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
