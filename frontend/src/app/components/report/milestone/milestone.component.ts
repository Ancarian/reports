import {Component, Input, OnInit} from '@angular/core';
import {Milestone} from '../../../core/model/model';

@Component({
  selector: 'app-milestone',
  templateUrl: './milestone.component.html'
})
export class MilestoneComponent implements OnInit {

  @Input() milestone: Milestone;

  constructor() {
  }

  ngOnInit() {
  }

}
