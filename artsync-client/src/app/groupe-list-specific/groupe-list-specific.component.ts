import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { UtilisateurService } from '../service/utilisateur.service';
import { Group } from '../models/group.model';

import { environment } from '../constants/environment.constant';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-groupe-list-specific',
  templateUrl: './groupe-list-specific.component.html',
  styleUrl: './groupe-list-specific.component.css'
})
export class GroupeListSpecificComponent {
  private _groupes: Group[] = [];
  private _subscriptions: Subscription[] = [];
  private _BASE_GROUPE_PHOTO_PATH: string = environment.apiUrl + '/media/images/utilisateur/';

  constructor(private utilisateurService: UtilisateurService, private route : ActivatedRoute) {}

  ngOnInit() {
    this.getUser();
  }

  ngOnDestroy() {
    this._subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  getUser(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this._subscriptions.push(
      this.utilisateurService.getGroupByUserId(id).subscribe(
        group => {
          this._groupes = group;
        }
      )
    );
  }
  get groupes(): Group[] {
    return this._groupes;
  }

  get BASE_GROUPE_PHOTO_PATH(): string {
    return this._BASE_GROUPE_PHOTO_PATH;
  }
}
