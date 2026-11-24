import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdministracionRoutingModule } from './administracion-routing-module';
import { Administracioncomponent } from './administracioncomponent/administracioncomponent';

@NgModule({
  declarations: [Administracioncomponent],
  imports: [CommonModule, AdministracionRoutingModule],
})
export class AdministracionModule {}
