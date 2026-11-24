import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Catalogocomponent } from './catalogocomponent/catalogocomponent';
import { Atrapabolsascomponent } from './atrapabolsascomponent/atrapabolsascomponent';
import { Historialcomponent } from './historialcomponent/historialcomponent';
import { Concursobailecomponent } from './concursobailecomponent/concursobailecomponent';
import { Carritosurfercomponent } from './carritosurfercomponent/carritosurfercomponent';
import { Pizzatroncomponent } from './pizzatroncomponent/pizzatroncomponent';
import { Cardjitsucomponent } from './cardjitsucomponent/cardjitsucomponent';

const routes: Routes = [
  { path: '', component: Catalogocomponent },
  { path: 'historial', component: Historialcomponent },
  { path: 'atrapabolsas', component: Atrapabolsascomponent },
  { path: 'concurso-baile', component: Concursobailecomponent },
  { path: 'carrito-surfero', component: Carritosurfercomponent},
  { path: 'pizzatron', component: Pizzatroncomponent },
  { path: 'card-jitsu', component: Cardjitsucomponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MinijuegosRoutingModule {}
