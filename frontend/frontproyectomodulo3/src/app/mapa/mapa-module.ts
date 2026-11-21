import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MapaRoutingModule } from './mapa-routing-module';
import { Mapacomponent } from './mapacomponent/mapacomponent';

@NgModule({
  declarations: [Mapacomponent],
  imports: [CommonModule, MapaRoutingModule],
})
export class MapaModule {}
