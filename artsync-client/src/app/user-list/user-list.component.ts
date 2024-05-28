import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent {
  constructor(private router: Router) {}

  onUserClick() {
    this.router.navigate([{ outlets: { mainView: ['specific'], specificPreview: ['user'] } }]);
  }
}
