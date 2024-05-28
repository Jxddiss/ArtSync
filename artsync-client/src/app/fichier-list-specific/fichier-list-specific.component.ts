import { Component, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-fichier-list-specific',
  templateUrl: './fichier-list-specific.component.html',
  styleUrl: './fichier-list-specific.component.css'
})
export class FichierListSpecificComponent {

  @ViewChild('dialog') dialog!: ElementRef;
  constructor() {}

  showDialog(): void {
    this.dialog.nativeElement.style.display = 'flex';
  }

  hideDialog(): void {
    this.dialog.nativeElement.style.display = 'none';
  }

  searchFile(name: string): void {
    if (!name) {
      //Ajouter la methode
      return;
    }
  }
}
