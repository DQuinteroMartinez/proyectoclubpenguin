import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { sesionguard } from './guards/sesionguard';

const routes: Routes = [
  {
    path: 'retos',
    canActivate: [sesionguard],
    loadChildren: () => import('./retos/retos-module').then((m) => m.RetosModule),
  },
  {
    path: 'rankings',
    canActivate: [sesionguard],
    loadChildren: () => import('./rankings/rankings-module').then((m) => m.RankingsModule),
  },
  {
    path: 'minijuegos',
    canActivate: [sesionguard],
    loadChildren: () => import('./minijuegos/minijuegos-module').then((m) => m.MinijuegosModule),
  },
  {
    path: 'mapa',
    canActivate: [sesionguard],
    loadChildren: () => import('./mapa/mapa-module').then((m) => m.MapaModule),
  },
  {
    path: 'usuarios',
    canActivate: [sesionguard],
    loadChildren: () => import('./usuarios/usuarios-module').then((m) => m.UsuariosModule),
  },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  {
    path: '',
    loadChildren: () =>
      import('./autenticacion/autenticacion-module').then((m) => m.AutenticacionModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
