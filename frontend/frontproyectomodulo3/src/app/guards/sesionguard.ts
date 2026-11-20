import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { Autenticacionservice } from '../services/autenticacionservice';

/**
 * Guard que exige una sesión iniciada para entrar a una ruta.
 * Si no hay sesión válida (o el token venció), redirige a /login.
 */
export const sesionguard: CanActivateFn = (route, state) => {
  const autenticacion = inject(Autenticacionservice);
  const router = inject(Router);

  if (autenticacion.haySesionValida()) {
  return true;
  }
  return router.createUrlTree(['/login']);
};
