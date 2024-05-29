import { Component, ElementRef, ViewChild, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { UtilisateurService } from '../service/utilisateur.service';
import { Utilisateur } from '../models/utilisateur.model';
import { Subscription } from 'rxjs';
import { environment } from '../constants/environment.constant';

declare function toggleReadonly(): void;

@Component({
  selector: 'app-user-preview',
  templateUrl: './user-preview.component.html',
  styleUrl: './user-preview.component.css'
})
export class UserPreviewComponent implements OnInit{

  utilisateur : Utilisateur | undefined;
  private _subscriptions : Subscription[] = [];
  private _BASE_UTILISATEUR_PHOTO_PATH : string = environment.apiUrl + '/media/images/utilisateur/';
  currentPhoto = ""
  constructor( 
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private utilisateurService: UtilisateurService
  ) { }

  ngOnInit(): void {
    this.getUtilisateur();
  }

  getUtilisateur(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this._subscriptions.push(
      this.utilisateurService.getUserById(id).subscribe(utilisateur => {
        this.utilisateur = utilisateur
        this.currentPhoto = this._BASE_UTILISATEUR_PHOTO_PATH+utilisateur.photoUrl
    })
    );
  }
  ngOnDestroy(): void {
    this._subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  isActive(url: string): boolean {
    return this.router.url === url;
  }

  @ViewChild('dialog') dialog!: ElementRef;
  toggleReadonly() {
    toggleReadonly();
  }
  
  showDialog(): void {
    this.dialog.nativeElement.style.display = 'flex';
  }

  hideDialog(): void {
    this.dialog.nativeElement.style.display = 'none';
  }
}
