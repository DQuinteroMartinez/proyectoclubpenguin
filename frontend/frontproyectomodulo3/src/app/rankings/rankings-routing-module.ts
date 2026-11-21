import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Rankingcomponent } from './rankingcomponent/rankingcomponent';

const routes: Routes = [{ path: '', component: Rankingcomponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RankingsRoutingModule {}
