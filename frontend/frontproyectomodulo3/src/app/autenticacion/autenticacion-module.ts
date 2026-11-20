import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AutenticacionRoutingModule } from './autenticacion-routing-module';
import { Logincomponent } from './logincomponent/logincomponent';
import { Registrocomponent } from './registrocomponent/registrocomponent';

@NgModule({
  declarations: [Logincomponent, Registrocomponent],
  imports: [CommonModule, AutenticacionRoutingModule],
})
export class AutenticacionModule {}
