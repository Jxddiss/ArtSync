import { Component } from '@angular/core';

@Component({
  selector: 'app-forum-list',
  templateUrl: './forum-list.component.html',
  styleUrl: './forum-list.component.css'
})
export class ForumListComponent {
  searchForum(name: string): void {
    if (!name) {
      //Ajouter la methode
      return;
    }
  }
}
