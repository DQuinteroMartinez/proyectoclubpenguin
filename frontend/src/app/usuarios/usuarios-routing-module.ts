import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Listausuarioscomponent } from './listausuarioscomponent/listausuarioscomponent';

const routes: Routes = [{path: '', component: Listausuarioscomponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UsuariosRoutingModule {}
