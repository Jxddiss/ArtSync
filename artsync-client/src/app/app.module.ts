import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { OptionsComponent } from './options/options.component';
import { GeneralViewComponent } from './general-view/general-view.component';
import { SpecificViewComponent } from './specific-view/specific-view.component';
import { UserListComponent } from './user-list/user-list.component';
import { SearchComponent } from './search/search.component';
import { GroupListComponent } from './group-list/group-list.component';
import { PostListComponent } from './post-list/post-list.component';
import { ForumListComponent } from './forum-list/forum-list.component';
import { UserPreviewComponent } from './user-preview/user-preview.component';
import { UserListSpecificComponent } from './user-list-specific/user-list-specific.component';
import { PostListSpecificComponent } from './post-list-specific/post-list-specific.component';
import { GroupeListSpecificComponent } from './groupe-list-specific/groupe-list-specific.component';
import { ForumListSpecificComponent } from './forum-list-specific/forum-list-specific.component';
import { FichierListSpecificComponent } from './fichier-list-specific/fichier-list-specific.component';
import { CommentListSpecificComponent } from './comment-list-specific/comment-list-specific.component';
import { ForumPreviewComponent } from './forum-preview/forum-preview.component';
import { GroupPreviewComponent } from './group-preview/group-preview.component';
import { PostPreviewComponent } from './post-preview/post-preview.component';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    OptionsComponent,
    GeneralViewComponent,
    SpecificViewComponent,
    UserListComponent,
    SearchComponent,
    GroupListComponent,
    PostListComponent,
    ForumListComponent,
    UserPreviewComponent,
    UserListSpecificComponent,
    PostListSpecificComponent,
    GroupeListSpecificComponent,
    ForumListSpecificComponent,
    FichierListSpecificComponent,
    CommentListSpecificComponent,
    ForumPreviewComponent,
    GroupPreviewComponent,
    PostPreviewComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
