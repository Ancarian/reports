import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NotFoundComponent} from './shared/errors/not-found/not-found.component';


const routes: Routes = [
  {path: '', redirectTo: 'lastReports', pathMatch: 'full'},
  {path: '404', component: NotFoundComponent},
  {path: '**', redirectTo: '404'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
