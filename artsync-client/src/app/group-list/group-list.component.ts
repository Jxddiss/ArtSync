import { Component, OnInit } from '@angular/core';
import { GroupService } from '../service/group.service';
import { Group } from '../models/group.model';
import { environment } from '../constants/environment.constant';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrl: './group-list.component.css'
})
export class GroupListComponent {
  groups: Group[];
  allgroups: Group[];
  private _subscriptions: Subscription[] = [];
  private _BASE_GROUPE_PHOTO_PATH : string = environment.apiUrl + '/media/images/utilisateur/';
  constructor(private groupService: GroupService) {
    this.groups = [];
    this.allgroups = [];
  }
  ngOnInit(){
    this._subscriptions.push(
      this.groupService.getAllGroups().subscribe(groups => {
        this.groups = groups
        this.allgroups = groups
        }
      )
    )
    
  }
  ngOnDestroy(){
    this._subscriptions.forEach(subscription => subscription.unsubscribe())
  }
  searchGroup(name: string): void {
    const searchResults : Group[] = this.allgroups
    if (!name) {
      this.groups = searchResults
      return;
    }

    this.groups = searchResults.filter((group : Group) =>
        group.titre.toLowerCase().indexOf(name.toLowerCase()) !== -1
    )
  }
  get BASE_GROUPE_PHOTO_PATH():string{
    return this._BASE_GROUPE_PHOTO_PATH
  }
}
