import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RestangularModule} from 'ngx-restangular';
import * as _ from "lodash"

import {AppComponent} from './app.component';
import {NavbarComponent} from './navbar/navbar.component';
import {PlaysComponent} from './plays/plays.component';

// Function for setting the default restangular configuration
export function RestangularConfigFactory(RestangularProvider) {
  RestangularProvider.setBaseUrl('http://localhost:8080/api/stats/');
  RestangularProvider.setResponseExtractor((response) => {
    var newResponse = response;
    if (_.isArray(response)) {
      _.forEach(newResponse, function (value, key) {
        newResponse[key].originalElement = _.clone(value);
      });
    } else {
      newResponse.originalElement = _.clone(response);
    }

    return newResponse;
  });
}

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    PlaysComponent
  ],
  imports: [
    BrowserModule,
    RestangularModule.forRoot(RestangularConfigFactory),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {

}
