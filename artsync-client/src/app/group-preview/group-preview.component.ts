import { Component, ElementRef, ViewChild, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { GroupService } from '../service/group.service';
import { Group } from '../models/group.model';

import { environment } from '../constants/environment.constant';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-group-preview',
  templateUrl: './group-preview.component.html',
  styleUrl: './group-preview.component.css'
})
export class GroupPreviewComponent implements OnInit{
  group : Group | undefined;
  private _subscriptions : Subscription[] = [];
  private _BASE_GROUPE_PHOTO_PATH : string = environment.apiUrl + '/media/images/utilisateur/';
  currentPhoto = ""

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private groupService: GroupService
  ) { }

  ngOnInit(): void {
    this.getGroup();

  }
  ngOnDestroy(): void {
    this._subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  getGroup(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this._subscriptions.push(
      this.groupService.getGroupById(id).subscribe(
        group => {
        this.group = group
        this.currentPhoto = this._BASE_GROUPE_PHOTO_PATH+group.projetPhoto
      })
    );
  }

  @ViewChild('dialog') dialog!: ElementRef;

  showDialog(): void {
    this.dialog.nativeElement.style.display = 'flex';
  }

  hideDialog(): void {
    this.dialog.nativeElement.style.display = 'none';
  }

  isActive(url: string): boolean {
    return this.router.url === url;
  } 

  onDeleteGroup(): void {
    if (!this.group) return
    this._subscriptions.push(
      this.groupService.deleteGroupById(this.group.id).subscribe((response) => {
        if (response.message === 'Success') {
          this.router.navigate(['/generalView', { outlets: { generalView: 'groups' } }]);
        }
      })
    )
    this.hideDialog();
  }
}
