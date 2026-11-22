import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Autenticacionservice } from '../services/autenticacionservice';

/**
 * Guard que exige sesión iniciada y rol ADMIN para entrar a una ruta.
 * Sin sesión redirige a /login; con sesión pero sin rol ADMIN, redirige al inicio.
 */

export const adminguard: CanActivateFn = () => {
  const autenticacion = inject(Autenticacionservice);
  const router = inject(Router);

  if (!autenticacion.haySesionValida()) {
    return router.createUrlTree(['/login']);
  }
  if (autenticacion.esAdmin()) {
  return true;
  }
  return router.createUrlTree(['/mapa']);
};
