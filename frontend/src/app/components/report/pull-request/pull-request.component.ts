import {Component, Input, OnInit} from '@angular/core';
import {Milestone, PullRequest} from '../../../core/model/model';

@Component({
  selector: 'app-pull-request',
  templateUrl: './pull-request.component.html'
})
export class PullRequestComponent implements OnInit {

  @Input() pullRequest: PullRequest;

  constructor() { }

  ngOnInit() {
  }
}
