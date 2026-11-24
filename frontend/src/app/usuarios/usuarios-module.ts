import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsuariosRoutingModule } from './usuarios-routing-module';
import { Listausuarioscomponent } from './listausuarioscomponent/listausuarioscomponent';

@NgModule({
  declarations: [Listausuarioscomponent],
  imports: [CommonModule, UsuariosRoutingModule],
})
export class UsuariosModule {}
