import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RankingsRoutingModule } from './rankings-routing-module';
import { Rankingcomponent } from './rankingcomponent/rankingcomponent';

@NgModule({
  declarations: [Rankingcomponent],
  imports: [CommonModule, RankingsRoutingModule],
})
export class RankingsModule {}
