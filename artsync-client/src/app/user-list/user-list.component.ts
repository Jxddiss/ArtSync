import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UtilisateurService } from '../service/utilisateur.service';
import { Utilisateur } from '../models/utilisateur.model';
import { Subscription } from 'rxjs';
import { environment } from '../constants/environment.constant';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent {
  private _utilisateurs : Utilisateur[]
  private _allUtilisateurs : Utilisateur[]
  private _subscriptions : Subscription[] = []
  private _BASE_UTILISATEUR_PHOTO_PATH : string = environment.apiUrl + '/media/images/utilisateur/';
  constructor(private router: Router, private utilisateurService : UtilisateurService) {
    this._utilisateurs = []
    this._allUtilisateurs = []
  }

  ngOnInit(){
    this._subscriptions.push(
      this.utilisateurService.getAllUsers().subscribe(utilisateurs => {
        this._utilisateurs = utilisateurs
        this._allUtilisateurs = utilisateurs
      })
    )
  }
  ngOnDestroy(){
    this._subscriptions.forEach(subscription => subscription.unsubscribe())
  }

  onUserClick() {
    this.router.navigate([{ outlets: { mainView: ['specific'], specificPreview: ['user'] } }]);
  }

  searchUser(name: string): void {
    const searchResults : Utilisateur[] = this._allUtilisateurs
    console.log(name)
    if (!name) {
      this._utilisateurs = searchResults
      return;
    }

    this._utilisateurs = searchResults.filter((user : Utilisateur) =>
        user.prenom.toLowerCase().indexOf(name.toLowerCase()) !== -1 ||
        user.nom.toLowerCase().indexOf(name.toLowerCase()) !== -1 ||
        user.pseudo.toLowerCase().indexOf(name.toLowerCase()) !== -1
    )
  }

  get utilisateurs():Utilisateur[]{
    return this._utilisateurs
  }
  get BASE_UTILISATEUR_PHOTO_PATH():string{
    return this._BASE_UTILISATEUR_PHOTO_PATH
  }
}
