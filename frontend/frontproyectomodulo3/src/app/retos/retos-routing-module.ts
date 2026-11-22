import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Retoscomponent } from './retoscomponent/retoscomponent';

const routes: Routes = [{ path: ' ', component: Retoscomponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RetosRoutingModule {}
