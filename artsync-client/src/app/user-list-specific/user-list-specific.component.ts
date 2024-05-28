import { Component } from '@angular/core';

@Component({
  selector: 'app-user-list-specific',
  templateUrl: './user-list-specific.component.html',
  styleUrl: './user-list-specific.component.css'
})
export class UserListSpecificComponent {
  searchUser(name: string): void {
    if (!name) {
      //Ajouter la methode
      return;
    }
  }
}
