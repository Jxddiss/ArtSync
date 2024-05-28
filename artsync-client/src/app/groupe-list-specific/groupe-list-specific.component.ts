import { Component } from '@angular/core';

@Component({
  selector: 'app-groupe-list-specific',
  templateUrl: './groupe-list-specific.component.html',
  styleUrl: './groupe-list-specific.component.css'
})
export class GroupeListSpecificComponent {
  searchGroup(name: string): void {
    if (!name) {
      //Ajouter la methode
      return;
    }
  }
}
