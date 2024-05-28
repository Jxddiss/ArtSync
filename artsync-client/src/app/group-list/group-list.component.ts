import { Component } from '@angular/core';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrl: './group-list.component.css'
})
export class GroupListComponent {
  searchGroup(name: string): void {
    if (!name) {
      //Ajouter la methode
      return;
    }
  }
}
