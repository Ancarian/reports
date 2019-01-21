import {Component, OnInit} from '@angular/core';
import {ReportServiceService} from '../../../core/service/report-service.service';
import {ActivatedRoute} from '@angular/router';
import {ErrorMessage} from '../../../core/model/model';

@Component({
  selector: 'app-not-found',
  templateUrl: './not-found.component.html'
})
export class NotFoundComponent implements OnInit {
  private time: string;
  private message: string;

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.time = this.route.snapshot.params['time'];
    this.message = this.route.snapshot.params['message'];
  }
}
