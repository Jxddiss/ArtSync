import { Component, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-comment-list-specific',
  templateUrl: './comment-list-specific.component.html',
  styleUrl: './comment-list-specific.component.css'
})
export class CommentListSpecificComponent {
  @ViewChild('dialog') dialog!: ElementRef;
  constructor() {}

  showDialog(): void {
    this.dialog.nativeElement.style.display = 'flex';
  }

  hideDialog(): void {
    this.dialog.nativeElement.style.display = 'none';
  }
  searchComment(name: string): void {
    if (!name) {
      //Ajouter la methode
      return;
    }
  }
}
