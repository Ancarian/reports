import {Component, OnInit} from '@angular/core';
import {ReportServiceService} from '../../core/service/report-service.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {Report, ReportLink} from '../../core/model/model';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html'
})
export class ReportComponent implements OnInit {
  private report: Report;
  private otherReports: ReportLink;

  private reportName;

  constructor(private reportService: ReportServiceService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      this.reportService.getReports(params.get('org'), params.get('repository')).then(
        data => {
          this.otherReports = data;
        }, error => {
          if (error.error.status === 404) {
            this.router.navigate(['/404', error.error]);
          }
        }
      );
      this.reportService.getReport(params.get('org'), params.get('repository'), params.get('report')).then(
        data => {
          this.reportName = params.get('report');
          this.report = data;
        }, error => {
          if (error.error.status === 404) {
            this.router.navigate(['/404', error.error]);
          }
        }
      );
    });
  }
}
