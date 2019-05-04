import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReportServiceService} from '../core/service/report-service.service';
import {OrganisationsComponent} from './organisations/organisations.component';
import {ComponentsRouterModule} from './components-router.module';
import {RouterModule} from '@angular/router';
import {ReportComponent} from './report/report.component';
import {IssueComponent} from './report/issue/issue.component';
import {MatChipsModule, MatExpansionModule, MatSelectModule, MatTabsModule} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {PerReleaseComponent} from './report/per-release/per-release.component';
import {ReleaseComponent} from './report/release/release.component';


@NgModule({
  declarations: [OrganisationsComponent, ReportComponent, IssueComponent, PerReleaseComponent, ReleaseComponent],
  imports: [
    BrowserAnimationsModule, CommonModule, MatTabsModule, MatSelectModule, MatChipsModule,
    MatExpansionModule, RouterModule, ComponentsRouterModule
  ],
  exports: [OrganisationsComponent],
  providers: [ReportServiceService]
})
export class ComponentsModule {
}
