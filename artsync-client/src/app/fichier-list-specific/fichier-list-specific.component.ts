import { Component } from '@angular/core';

@Component({
  selector: 'app-fichier-list-specific',
  templateUrl: './fichier-list-specific.component.html',
  styleUrl: './fichier-list-specific.component.css'
})
export class FichierListSpecificComponent {
  searchFile(name: string): void {
    if (!name) {
      //Ajouter la methode
      return;
    }
  }
}
