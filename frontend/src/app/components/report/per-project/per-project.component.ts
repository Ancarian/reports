import {Component, Input, OnInit} from '@angular/core';
import {PerProject} from '../../../core/model/model';

@Component({
  selector: 'app-per-project',
  templateUrl: './per-project.component.html'
})
export class PerProjectComponent implements OnInit {
  @Input() perProject: PerProject;
  constructor() { }

  ngOnInit() {
  }

}
