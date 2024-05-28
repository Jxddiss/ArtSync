import { Component, ElementRef, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-group-preview',
  templateUrl: './group-preview.component.html',
  styleUrl: './group-preview.component.css'
})
export class GroupPreviewComponent {
  @ViewChild('dialog') dialog!: ElementRef;
  constructor(private router: Router, private route: ActivatedRoute) { }

  showDialog(): void {
    this.dialog.nativeElement.style.display = 'flex';
  }

  hideDialog(): void {
    this.dialog.nativeElement.style.display = 'none';
  }

  isActive(url: string): boolean {
    return this.router.url === url;
  }
  
}
