import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MinijuegosRoutingModule } from './minijuegos-routing-module';
import { Atrapabolsasminijuego } from './atrapabolsasminijuego/atrapabolsasminijuego';
import { Atrapabolsascomponent } from './atrapabolsascomponent/atrapabolsascomponent';

@NgModule({
  declarations: [Atrapabolsasminijuego, Atrapabolsascomponent],
  imports: [CommonModule, MinijuegosRoutingModule],
})
export class MinijuegosModule {}
