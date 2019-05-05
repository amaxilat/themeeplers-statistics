import {Component, Input, OnInit} from '@angular/core';
import {Restangular} from 'ngx-restangular';

@Component({
  selector: 'app-plays',
  templateUrl: './plays.component.html',
  styleUrls: ['./plays.component.css']
})
export class PlaysComponent implements OnInit {

  @Input() siteTitle;
  @Input() siteSubtitle;
  stats = {};

  constructor(private restangular: Restangular) {
  }

  ngOnInit() {

    this.stats = this.restangular.one('general', 1).get();
    console.log(this.stats);
  }

}
