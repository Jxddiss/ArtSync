import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { CommentService } from '../service/comment.service';
import { Comment } from '../models/comment.model';
import { ForumService } from '../service/forum.service';
import { Subscription } from 'rxjs';
import { PostService } from '../service/post.service';

@Component({
  selector: 'app-comment-list-specific',
  templateUrl: './comment-list-specific.component.html',
  styleUrl: './comment-list-specific.component.css'
})
export class CommentListSpecificComponent implements OnInit {
  comments: Comment[] = [];
  private _subscriptions: Subscription[] = [];


  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private commentService: CommentService,
    private forumService: ForumService,
    private postService:PostService
  ) { }

  ngOnInit(): void {
    this.getComments();
  }

  getComments(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    const path = this.location.path();
    if (path.includes('forum')) {
      this._subscriptions.push(
        this.forumService.getCommentaireByForumId(id).subscribe(comments => {
          this.comments = comments;
        })
      );
    } else if (path.includes('user')) {
      // Pour utilisateurs
    } else if (path.includes('post')) {
      this._subscriptions.push(
        this.postService.getCommentaireByPostId(id).subscribe(comments => this.comments = comments)
      );
    }
  }

  ngOnDestroy(): void {
    this._subscriptions.forEach(subscription => subscription.unsubscribe());
  }


  @ViewChild('dialog') dialog!: ElementRef;

  showDialog(): void {
    this.dialog.nativeElement.style.display = 'flex';
  }

  hideDialog(): void {
    this.dialog.nativeElement.style.display = 'none';
  }
}
