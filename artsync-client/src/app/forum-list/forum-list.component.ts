import { Component, OnInit } from '@angular/core';
import { ForumService } from '../service/forum.service';
import { Forum } from '../models/forum.model';

@Component({
  selector: 'app-forum-list',
  templateUrl: './forum-list.component.html',
  styleUrl: './forum-list.component.css'
})
export class ForumListComponent implements OnInit {
  forums: Forum[];
  constructor(private forumService: ForumService) {
    this.forums = [];    
  }
  ngOnInit(): void {
    this.forums = this.forumService.getAllForums();
  }

  searchForum(name: string): void {
    if (name != null) {
      const result = this.forumService.getForumByTitle(name);
      this.forums = result ? [result] : []
    }
  }
}
