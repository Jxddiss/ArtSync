import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GeneralViewComponent } from './general-view/general-view.component';
import { UserListComponent } from './user-list/user-list.component';
import { GroupListComponent } from './group-list/group-list.component';
import { PostListComponent } from './post-list/post-list.component';
import { ForumListComponent } from './forum-list/forum-list.component';
import { SpecificViewComponent } from './specific-view/specific-view.component';
import { UserPreviewComponent } from './user-preview/user-preview.component';
import { GroupPreviewComponent } from './group-preview/group-preview.component';
import { ForumPreviewComponent } from './forum-preview/forum-preview.component';
import { CommentListSpecificComponent } from './comment-list-specific/comment-list-specific.component';
import { ForumListSpecificComponent } from './forum-list-specific/forum-list-specific.component';
import { PostListSpecificComponent } from './post-list-specific/post-list-specific.component';
import { GroupeListSpecificComponent } from './groupe-list-specific/groupe-list-specific.component';
import { FichierListSpecificComponent } from './fichier-list-specific/fichier-list-specific.component';
import { UserListSpecificComponent } from './user-list-specific/user-list-specific.component';
import { PostPreviewComponent } from './post-preview/post-preview.component';
import { LoginComponent } from './login/login.component';
import { AuthenticationGuard } from './guard/authentication.guard';

const routes: Routes = [
  //---------------------------------------------------------------------------------GENERAL VIEWS---------------------------------------------------------------------------------
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {path:'login',component:LoginComponent},
  { 
    path: 'generalView', 
    component: GeneralViewComponent, 
    children: [
      { path: '', component: UserListComponent, outlet: 'generalView' },
      { path: 'groups', component: GroupListComponent, outlet: 'generalView' },
      { path: 'posts', component: PostListComponent, outlet: 'generalView' },
      { path: 'forums', component: ForumListComponent, outlet: 'generalView' }
    ],
    canActivate: [AuthenticationGuard]
  },
  //---------------------------------------------------------------------------------SPECIAL VIEWS---------------------------------------------------------------------------------
  //------------USER PREVIEW---------------- <<<<<<<<<<
  //USER POSTS
  {
    path: 'overview/user/posts/:id',
    component: SpecificViewComponent,
    children: [
      { path: '', component: UserPreviewComponent, outlet: 'specificPreview' },
      { path: '', component: PostListSpecificComponent, outlet: 'specificListView' }
    ],
    canActivate: [AuthenticationGuard]
  },
  //USER GROUPS
  {
    path: 'overview/user/groups/:id',
    component: SpecificViewComponent,
    children: [
      { path: '', component: UserPreviewComponent, outlet: 'specificPreview'},
      { path: '', component: GroupeListSpecificComponent, outlet: 'specificListView' }
    ],
    canActivate: [AuthenticationGuard]
  },
  //USER FILES
  {
    path: 'overview/user/files/:id',
    component: SpecificViewComponent,
    children: [
      { path: '', component: UserPreviewComponent, outlet: 'specificPreview' },
      { path: '', component: FichierListSpecificComponent, outlet: 'specificListView'}
    ],
    canActivate: [AuthenticationGuard]
  },
  //USER FORUMS
  {
    path: 'overview/user/forums/:id',
    component: SpecificViewComponent,
    children: [
      { path: '', component: UserPreviewComponent, outlet: 'specificPreview' },
      { path: '', component: ForumListSpecificComponent, outlet: 'specificListView' }
    ],
    canActivate: [AuthenticationGuard]
  },
  //USER COMMENTS
  {
    path: 'overview/user/comments/:id',
    component: SpecificViewComponent,
    children: [
      { path: '', component: UserPreviewComponent, outlet: 'specificPreview' },
      { path: '', component: CommentListSpecificComponent, outlet: 'specificListView' }
    ],
    canActivate: [AuthenticationGuard]
  },

  //------------GROUP PREVIEW---------------- <<<<<<<<<<
  //GROUP USERS
  { 
    path: 'overview/group/users/:id',
    component: SpecificViewComponent,
    children: [
      { path: '', component: GroupPreviewComponent, outlet: 'specificPreview' },
      { path: '', component: UserListSpecificComponent, outlet: 'specificListView' }
    ],
    canActivate: [AuthenticationGuard]
  },
  //GROUP FILES
  {
    path: 'overview/group/files/:id',
    component: SpecificViewComponent,
    children: [
      { path: '', component: GroupPreviewComponent, outlet: 'specificPreview' },
      { path: '', component: FichierListSpecificComponent, outlet: 'specificListView' }
    ],
    canActivate: [AuthenticationGuard]
  },

  //------------POST PREVIEW---------------- <<<<<<<<<<
  //POST COMMENTS
  {
    path: 'overview/post/comments/:id',
    component: SpecificViewComponent,
    children: [
      { path: '', component: PostPreviewComponent, outlet: 'specificPreview' },
      { path: '', component: CommentListSpecificComponent, outlet: 'specificListView' }
    ],
    canActivate: [AuthenticationGuard]
  },

  //------------FORUM PREVIEW---------------- <<<<<<<<<<
  //FORUM COMMENTS
  { 
    path: 'overview/forum/comments/:id',
    component: SpecificViewComponent,
    children: [
      { path: '', component: ForumPreviewComponent, outlet: 'specificPreview' },
      { path: '', component: CommentListSpecificComponent, outlet: 'specificListView' }
    ],
    canActivate: [AuthenticationGuard]
  },
  { path: '**', redirectTo: 'generalView/users' }

];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }