import { Component, ElementRef, ViewChild, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { ForumService } from '../service/forum.service';
import { Forum } from '../models/forum.model';
import { Utilisateur } from '../models/utilisateur.model';
import { environment } from '../constants/environment.constant';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-forum-preview',
  templateUrl: './forum-preview.component.html',
  styleUrl: './forum-preview.component.css'
})
export class ForumPreviewComponent {
  forum : Forum | undefined;
  private _subscriptions : Subscription[] = [];
  private _BASE_USER_PHOTO_PATH : string = environment.apiUrl + '/media/images/utilisateur/';
  currentPhoto = ""
  constructor( 
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private forumService: ForumService
  ) { }
  
  ngOnInit(): void {
    this.getForum();
  }

  getForum(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this._subscriptions.push(
      this.forumService.getForumById(id).subscribe(forum => {
        this.forum = forum
        this.currentPhoto = this._BASE_USER_PHOTO_PATH+forum.utilisateur.photoUrl
    })
    );
  }

  ngOnDestroy(): void {
    this._subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  get BASE_USER_PHOTO_PATH():string{
    return this._BASE_USER_PHOTO_PATH
  }

  @ViewChild('dialog') dialog!: ElementRef;
  showDialog(): void {
    this.dialog.nativeElement.style.display = 'flex';
  }

  hideDialog(): void {
    this.dialog.nativeElement.style.display = 'none';
  }
}
