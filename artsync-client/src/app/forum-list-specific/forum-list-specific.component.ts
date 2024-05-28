import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { ForumService } from '../service/forum.service';
import { Forum } from '../models/forum.model';

@Component({
  selector: 'app-forum-list-specific',
  templateUrl: './forum-list-specific.component.html',
  styleUrl: './forum-list-specific.component.css'
})
export class ForumListSpecificComponent implements OnInit {
  forums: Forum[] = [];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private forumService: ForumService
  ) { }

  ngOnInit(): void {
    this.getForums();
  }

  getForums(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.forums = this.forumService.getForumsByUserId(id);
  }
  
  searchForum(name: string): void {
    if (!name) {
      //Ajouter la methode
      return;
    }
  }
}
