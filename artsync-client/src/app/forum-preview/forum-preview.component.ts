import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-forum-preview',
  templateUrl: './forum-preview.component.html',
  styleUrl: './forum-preview.component.css'
})
export class ForumPreviewComponent {
  @ViewChild('dialog') dialog!: ElementRef;
  constructor() { }
  showDialog(): void {
    this.dialog.nativeElement.style.display = 'flex';
  }

  hideDialog(): void {
    this.dialog.nativeElement.style.display = 'none';
  }
}
