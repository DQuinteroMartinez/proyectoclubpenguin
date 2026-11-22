import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MinijuegosRoutingModule } from './minijuegos-routing-module';
import { Atrapabolsascomponent } from './atrapabolsascomponent/atrapabolsascomponent';
import { Catalogocomponent } from './catalogocomponent/catalogocomponent';
import { Historialcomponent } from './historialcomponent/historialcomponent';
import { Concursobailecomponent } from './concursobailecomponent/concursobailecomponent';
import { Carritosurfercomponent } from './carritosurfercomponent/carritosurfercomponent';

@NgModule({
  declarations: [
    Atrapabolsascomponent,
    Catalogocomponent,
    Historialcomponent,
    Concursobailecomponent,
    Carritosurfercomponent,
  ],
  imports: [CommonModule, MinijuegosRoutingModule],
})
export class MinijuegosModule {}
