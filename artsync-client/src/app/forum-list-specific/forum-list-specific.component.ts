import { Component } from '@angular/core';

@Component({
  selector: 'app-forum-list-specific',
  templateUrl: './forum-list-specific.component.html',
  styleUrl: './forum-list-specific.component.css'
})
export class ForumListSpecificComponent {
  searchForum(name: string): void {
    if (!name) {
      //Ajouter la methode
      return;
    }
  }
}
