import { Component } from '@angular/core';

@Component({
  selector: 'app-post-list-specific',
  templateUrl: './post-list-specific.component.html',
  styleUrl: './post-list-specific.component.css'
})
export class PostListSpecificComponent {
  searchPost(name: string): void {
    if (!name) {
      //Ajouter la methode
      return;
    }
  }
}
