import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { OptionsComponent } from './options/options.component';
import { GeneralViewComponent } from './general-view/general-view.component';
import { SpecificViewComponent } from './specific-view/specific-view.component';
import { UserListComponent } from './user-list/user-list.component';
import { SearchComponent } from './search/search.component';
import { GroupListComponent } from './group-list/group-list.component';

@NgModule({
  declarations: [
    AppComponent,
    OptionsComponent,
    GeneralViewComponent,
    SpecificViewComponent,
    UserListComponent,
    SearchComponent,
    GroupListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
