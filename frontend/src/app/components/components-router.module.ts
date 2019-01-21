import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OrganisationsComponent} from './organisations/organisations.component';
import {ReportComponent} from './report/report.component';


const routes: Routes = [
  {path: 'organisation/:org/repository/:repository/report/:report', component: ReportComponent},
  {path: 'lastReports', component: OrganisationsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class ComponentsRouterModule {
}
