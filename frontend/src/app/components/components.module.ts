import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReportServiceService} from '../core/service/report-service.service';
import {OrganisationsComponent} from './organisations/organisations.component';
import {ComponentsRouterModule} from './components-router.module';
import {RouterModule} from '@angular/router';
import {ReportComponent} from './report/report.component';
import {IssueComponent} from './report/issue/issue.component';
import {MilestoneComponent} from './report/milestone/milestone.component';
import {PullRequestComponent} from './report/pull-request/pull-request.component';
import {MatChipsModule, MatExpansionModule, MatSelectModule, MatTabsModule} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {PerProjectComponent} from './report/per-project/per-project.component';
import {PerReleaseComponent} from './report/per-release/per-release.component';
import {PerMilestoneComponent} from './report/per-milestone/per-milestone.component';
import {ReleaseComponent} from './report/release/release.component';


@NgModule({
  declarations: [OrganisationsComponent, ReportComponent, IssueComponent,
    MilestoneComponent, PullRequestComponent, PerProjectComponent, PerReleaseComponent, PerMilestoneComponent, ReleaseComponent],
  imports: [
    BrowserAnimationsModule, CommonModule, MatTabsModule, MatSelectModule, MatChipsModule,
    MatExpansionModule, RouterModule, ComponentsRouterModule
  ],
  exports: [OrganisationsComponent],
  providers: [ReportServiceService]
})
export class ComponentsModule {
}
