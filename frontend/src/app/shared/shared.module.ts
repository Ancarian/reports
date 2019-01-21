import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {NotFoundComponent} from './errors/not-found/not-found.component';
import {RouterModule} from '@angular/router';

@NgModule({
  declarations: [NotFoundComponent],
  imports: [
    CommonModule, RouterModule
  ]
})
export class SharedModule { }
