import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GeneralViewComponent } from './general-view/general-view.component';
import { UserListComponent } from './user-list/user-list.component';
import { GroupListComponent } from './group-list/group-list.component';
import { PostListComponent } from './post-list/post-list.component';
import { ForumListComponent } from './forum-list/forum-list.component';
import { SpecificViewComponent } from './specific-view/specific-view.component';

const routes: Routes = [
  {
    path: '',
    component: GeneralViewComponent,
    children: [
      { path: 'users', component: UserListComponent, outlet: 'generalView' },
      { path: 'groups', component: GroupListComponent, outlet: 'generalView' },
      { path: 'posts', component: PostListComponent, outlet: 'generalView' },
      { path: 'forums', component: ForumListComponent, outlet: 'generalView' },
    ]
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }