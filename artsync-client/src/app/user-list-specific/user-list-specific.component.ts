import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { GroupService } from '../service/group.service';
import { Utilisateur } from '../models/utilisateur.model';

import { environment } from '../constants/environment.constant';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-user-list-specific',
  templateUrl: './user-list-specific.component.html',
  styleUrl: './user-list-specific.component.css'
})
export class UserListSpecificComponent {
  _users: Utilisateur[] = [];
  private _subscriptions: Subscription[] = [];
  private _BASE_UTILISATEUR_PHOTO_PATH: string = environment.apiUrl + '/media/images/utilisateur/';

  constructor(private groupService: GroupService, private route : ActivatedRoute) {}

  ngOnInit() {
    this.getGroup();
  }

  ngOnDestroy() {
    this._subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  getGroup(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this._subscriptions.push(
      this.groupService.getGroupById(id).subscribe(
        group => {
          this._users = group.utilisateurs;
        }
      )
    );
  }
  get users(): Utilisateur[] {
    return this._users;
  }

  get BASE_UTILISATEUR_PHOTO_PATH(): string {
    return this._BASE_UTILISATEUR_PHOTO_PATH;
  }
}
