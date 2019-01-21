import {Component, Input, OnInit} from '@angular/core';
import {Milestone, PerRelease} from '../../../core/model/model';

@Component({
  selector: 'app-per-milestone',
  templateUrl: './per-milestone.component.html'
})
export class PerMilestoneComponent implements OnInit {
  @Input() milestones: Milestone[];

  constructor() {
  }

  ngOnInit() {
  }

}
