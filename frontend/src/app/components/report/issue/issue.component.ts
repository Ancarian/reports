import {Component, Input, OnInit} from '@angular/core';
import {Issue} from '../../../core/model/model';

@Component({
  selector: 'app-issue',
  templateUrl: './issue.component.html'
})
export class IssueComponent implements OnInit {

  @Input() issues: Issue[];

  constructor() {
  }

  ngOnInit() {
  }
}
