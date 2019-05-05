import {Component, OnInit} from '@angular/core';
import {RestApiService} from "../../service/rest-api.service";

@Component({
  selector: 'app-nights',
  templateUrl: './nights.component.html',
  styleUrls: ['./nights.component.css']
})
export class NightsComponent implements OnInit {
  dtOptions: DataTables.Settings = {};
  GameNight: any = [];

  constructor(public restApi: RestApiService) {
  }

  ngOnInit() {
    this.dtOptions = {
      pagingType: 'full_numbers'
    };
    this.loadGameNights()
  }

  loadGameNights() {
    return this.restApi.getGameNights().subscribe((data: {}) => {
      this.GameNight = data;
    })
  }

}
