import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MinijuegosRoutingModule } from './minijuegos-routing-module';
import { Atrapabolsascomponent } from './atrapabolsascomponent/atrapabolsascomponent';
import { Catalogocomponent } from './catalogocomponent/catalogocomponent';
import { Historialcomponent } from './historialcomponent/historialcomponent';
import { Concursobailecomponent } from './concursobailecomponent/concursobailecomponent';

@NgModule({
  declarations: [
    Atrapabolsascomponent,
    Catalogocomponent,
    Historialcomponent,
    Concursobailecomponent,
  ],
  imports: [CommonModule, MinijuegosRoutingModule],
})
export class MinijuegosModule {}
