import { TestBed } from '@angular/core/testing';
import { AutenticacioninterceptorInterceptor } from './autenticacioninterceptor-interceptor';

describe('AutenticacioninterceptorInterceptor', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      providers: [AutenticacioninterceptorInterceptor],
    }),
  );

  it('should be created', () => {
    const interceptor: AutenticacioninterceptorInterceptor = TestBed.inject(
      AutenticacioninterceptorInterceptor,
    );
    expect(interceptor).toBeTruthy();
  });
});
