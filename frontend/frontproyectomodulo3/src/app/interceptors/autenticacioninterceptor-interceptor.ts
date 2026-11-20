import { Injectable, Injector } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from '../../environments/environment';
import { Router } from '@angular/router';
import { Autenticacionservice } from '../services/autenticacionservice';

@Injectable()
export class AutenticacioninterceptorInterceptor implements HttpInterceptor {
  private readonly rutasPublicas = [
    `${environment.apiUrl}/autenticacion/login`,
    `${environment.apiUrl}/usuarios/registro`,
  ];

  constructor(
    private injector: Injector,
    private router: Router,
  ) {}

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if(!req.url.startsWith(environment.apiUrl) || this.rutasPublicas.includes(req.url)) {
      return next.handle(req);
    }

    const autenticacion = this.injector.get(Autenticacionservice);
    const token = autenticacion.obtenerToken();

    const peticion = token ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } }) : req;

    return next.handle(peticion).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status == 401) {
          autenticacion.cerrarSesion();
          this.router.navigate(['/login']);
        }
        return throwError(() => error );
      })
    );
  }
}
