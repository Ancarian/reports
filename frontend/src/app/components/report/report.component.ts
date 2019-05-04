import {Component, OnInit} from '@angular/core';
import {ReportServiceService} from '../../core/service/report-service.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {Report, ReportLink} from '../../core/model/model';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html'
})
export class ReportComponent implements OnInit {
  get reportName() {
    return this._reportName;
  }

  get otherReports(): ReportLink {
    return this._otherReports;
  }

  get report(): Report {
    return this._report;
  }

  private _report: Report;
  private _otherReports: ReportLink;

  private _reportName;

  constructor(private reportService: ReportServiceService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      this.reportService.getReports(params.get('org'), params.get('repository')).then(
        data => {
          this._otherReports = data;
        }, error => {
          if (error.error.status === 404) {
            this.router.navigate(['/404', error.error]);
          }
        }
      );
      this.reportService.getReport(params.get('org'), params.get('repository'), params.get('report')).then(
        data => {
          this._reportName = params.get('_report');
          console.log(data);
          this._report = data;
        }, error => {
          if (error.error.status === 404) {
            this.router.navigate(['/404', error.error]);
          }
        }
      );
    });
  }
}
