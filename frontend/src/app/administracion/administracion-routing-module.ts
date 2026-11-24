import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Administracioncomponent } from './administracioncomponent/administracioncomponent';

const routes: Routes = [{ path: '', component: Administracioncomponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdministracionRoutingModule {}
