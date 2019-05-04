import {Component, Input, OnInit} from '@angular/core';
import {PerRelease} from '../../../core/model/model';

@Component({
  selector: 'app-per-release',
  templateUrl: './per-release.component.html'
})
export class PerReleaseComponent implements OnInit {

  @Input() perRelease: PerRelease[];

  constructor() { }

  ngOnInit() {
  }

}
