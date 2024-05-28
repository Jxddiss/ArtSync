import { Component } from '@angular/core';
declare function toggleReadonly(): void;
@Component({
  selector: 'app-user-preview',
  templateUrl: './user-preview.component.html',
  styleUrl: './user-preview.component.css'
})
export class UserPreviewComponent {
  toggleReadonly() {
    toggleReadonly();
  }
}
