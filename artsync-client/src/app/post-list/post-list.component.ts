import { Component } from '@angular/core';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css'
})
export class PostListComponent {
  searchPost(name: string): void {
    if (!name) {
      //Ajouter la methode
      return;
    }
  }
}
