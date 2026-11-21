import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Atrapabolsasminijuego } from './atrapabolsasminijuego/atrapabolsasminijuego';

const routes: Routes = [
  { path: 'atrapabolsas', component: Atrapabolsasminijuego }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MinijuegosRoutingModule {}
