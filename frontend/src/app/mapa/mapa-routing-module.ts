import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Mapacomponent } from './mapacomponent/mapacomponent';

const routes: Routes = [{ path: '', component: Mapacomponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MapaRoutingModule {}
