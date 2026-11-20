import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Logincomponent } from './logincomponent/logincomponent';
import { Registrocomponent } from './registrocomponent/registrocomponent';

const routes: Routes = [
  { path: 'login', component: Logincomponent },
  { path: 'registro', component: Registrocomponent, }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AutenticacionRoutingModule {}
