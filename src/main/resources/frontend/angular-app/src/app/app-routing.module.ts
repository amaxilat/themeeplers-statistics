import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {NightsComponent} from './component/nights/nights.component';
import {PlaysComponent} from "./component/plays/plays.component";

const routes: Routes = [
  {path: 'gamenights', component: NightsComponent},
  {path: 'plays', component: PlaysComponent},
  {path: '', component: PlaysComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
