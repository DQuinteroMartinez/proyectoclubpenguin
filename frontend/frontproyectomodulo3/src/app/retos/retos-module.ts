import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RetosRoutingModule } from './retos-routing-module';
import { Retoscomponent } from './retoscomponent/retoscomponent';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [Retoscomponent],
  imports: [CommonModule, RetosRoutingModule, ReactiveFormsModule
  ],
})
export class RetosModule {}
