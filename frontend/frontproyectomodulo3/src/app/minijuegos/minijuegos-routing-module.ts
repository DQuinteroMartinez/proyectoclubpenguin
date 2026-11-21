import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Catalogocomponent } from './catalogocomponent/catalogocomponent';
import { Atrapabolsascomponent } from './atrapabolsascomponent/atrapabolsascomponent';
import { Historialcomponent } from './historialcomponent/historialcomponent';

const routes: Routes = [
  { path: '', component: Catalogocomponent },
  { path: 'historial', component: Historialcomponent },
  { path: 'atrapabolsas', component: Atrapabolsascomponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MinijuegosRoutingModule {}
