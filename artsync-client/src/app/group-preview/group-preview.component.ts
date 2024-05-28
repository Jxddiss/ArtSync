import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-group-preview',
  templateUrl: './group-preview.component.html',
  styleUrl: './group-preview.component.css'
})
export class GroupPreviewComponent {
  constructor(private router: Router, private route: ActivatedRoute) { }

  isActive(url: string): boolean {
    return this.router.url === url;
  }
}
