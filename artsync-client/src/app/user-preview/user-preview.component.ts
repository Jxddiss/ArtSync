import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
declare function toggleReadonly(): void;
@Component({
  selector: 'app-user-preview',
  templateUrl: './user-preview.component.html',
  styleUrl: './user-preview.component.css'
})
export class UserPreviewComponent {
  constructor(private router: Router, private route: ActivatedRoute) { }

  isActive(url: string): boolean {
    return this.router.url === url;
  }
  toggleReadonly() {
    toggleReadonly();
  }
}
