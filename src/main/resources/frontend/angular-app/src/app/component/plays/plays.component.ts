import {Component, OnInit} from '@angular/core';
import {RestApiService} from "../../service/rest-api.service";

@Component({
  selector: 'app-plays',
  templateUrl: './plays.component.html',
  styleUrls: ['./plays.component.css']
})
export class PlaysComponent implements OnInit {
  dtOptions: DataTables.Settings = {};
  General: any = [];
  Plays: any = [];

  constructor(public restApi: RestApiService) {
  }

  ngOnInit() {
    this.dtOptions = {
      pagingType: 'full_numbers'
    };
    this.loadGeneralStats();
    this.loadPlays();
  }

  loadGeneralStats() {
    return this.restApi.getGeneralStats().subscribe((data: {}) => {
      this.General = data;
    })
  }

  loadPlays() {
    return this.restApi.getPlays().subscribe((data: {}) => {
      this.Plays = data;
    })
  }
}
