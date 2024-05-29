import { Component, ElementRef, ViewChild } from '@angular/core';
import { Demande } from '../models/demande.model';
import { Subscription } from 'rxjs';
import { DemandeService } from '../service/demande.service';

@Component({
  selector: 'app-demandes',
  templateUrl: './demandes.component.html',
  styleUrl: './demandes.component.css'
})
export class DemandesComponent {
    private _demandes : Demande[] = [];
    private _selectedDemande : Demande | undefined
    private _subscriptions : Subscription[] = [];

    constructor(private demandeService : DemandeService) { }

    ngOnInit(): void {
      this.getDemandes();
    }

    ngOnDestroy(): void {
      this._subscriptions.forEach(sub => sub.unsubscribe());
    }

    getDemandes() {
      this._subscriptions.push(
        this.demandeService.getDemandes().subscribe(demandes => this._demandes = demandes)
      );
    }

    get demandes() : Demande[] {
      return this._demandes;
    }

    @ViewChild('dialog') dialog!: ElementRef;

    showDialog(demande : Demande): void {
      this._selectedDemande = demande
      this.dialog.nativeElement.style.display = 'flex';
    }

    hideDialog(): void {
      this.dialog.nativeElement.style.display = 'none';
    }

    onDeleteDemande(): void {
      if (this._selectedDemande) {
        this._subscriptions.push(
          this.demandeService.deleteDemande(this._selectedDemande).subscribe((reponse) => {
            if (reponse.message.includes('Demande supprimmé')) {
              this.getDemandes();
            }
          })
        );
      }
      this.hideDialog();
    }
}
