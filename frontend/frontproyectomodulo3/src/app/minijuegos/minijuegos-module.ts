import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MinijuegosRoutingModule } from './minijuegos-routing-module';
import { Atrapabolsascomponent } from './atrapabolsascomponent/atrapabolsascomponent';
import { Catalogocomponent } from './catalogocomponent/catalogocomponent';
import { Historialcomponent } from './historialcomponent/historialcomponent';

@NgModule({
  declarations: [Atrapabolsascomponent, Catalogocomponent, Historialcomponent],
  imports: [CommonModule, MinijuegosRoutingModule],
})
export class MinijuegosModule {}
