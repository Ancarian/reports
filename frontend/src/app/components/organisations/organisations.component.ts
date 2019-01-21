import {Component, OnInit} from '@angular/core';
import {ReportServiceService} from '../../core/service/report-service.service';
import {ReportLink} from '../../core/model/model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-organisations',
  templateUrl: './organisations.component.html'
})
export class OrganisationsComponent implements OnInit {
  lastReports: ReportLink[];

  constructor(private reportService: ReportServiceService, private router: Router) {
  }

  ngOnInit() {
    this.reportService.getLastReports().then(data => {
      this.lastReports = data;
      console.log(data);
    }, error => {
      console.log(error.error);
    });
  }

}
