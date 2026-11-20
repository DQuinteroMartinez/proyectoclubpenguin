import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AutenticacionRoutingModule } from './autenticacion-routing-module';
import { Logincomponent } from './logincomponent/logincomponent';
import { Registrocomponent } from './registrocomponent/registrocomponent';
import { ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [Logincomponent, Registrocomponent],
  imports: [CommonModule, AutenticacionRoutingModule, ReactiveFormsModule],
})
export class AutenticacionModule {}
