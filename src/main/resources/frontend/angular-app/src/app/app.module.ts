import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import { DataTablesModule } from 'angular-datatables';

import {AppComponent} from './component/app.component';
import {NavbarComponent} from './component/navbar/navbar.component';
import {PlaysComponent} from './component/plays/plays.component';
import {NightsComponent} from './component/nights/nights.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    PlaysComponent,
    NightsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    DataTablesModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
