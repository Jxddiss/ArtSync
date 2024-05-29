import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { ForumService } from '../service/forum.service';
import { Forum } from '../models/forum.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-forum-list-specific',
  templateUrl: './forum-list-specific.component.html',
  styleUrl: './forum-list-specific.component.css'
})
export class ForumListSpecificComponent implements OnInit{
  forums: Forum[] = [];
  private _subscriptions: Subscription[] = [];
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private forumService: ForumService
  ) { }

  ngOnInit(): void {
    this.getForums();
  }

  ngOnDestroy(): void {
    this._subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  getForums(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this._subscriptions.push(
      this.forumService.getAllForumByUtilisateurId(id).subscribe(
        forum => {
          this.forums = forum;
        }
      )
    );
  }
  
}
