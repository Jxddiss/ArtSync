import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { CommentService } from '../service/comment.service';
import { Comment } from '../models/comment.model';

@Component({
  selector: 'app-comment-list-specific',
  templateUrl: './comment-list-specific.component.html',
  styleUrl: './comment-list-specific.component.css'
})
export class CommentListSpecificComponent implements OnInit {
  comments: Comment[] = [];
  

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private commentService: CommentService
  ) { }


  ngOnInit(): void {
    this.getComments();
  }

  getComments(): void {
    const path = this.router.url.split('/');
    const id = Number(path[path.length - 1]);
    if (path.includes('forum')) {
      this.comments = this.commentService.getCommentByForumId(id);
    } else if (path.includes('user')) {
      this.comments = this.commentService.getCommentByUserIds(id);
    }
  }


  @ViewChild('dialog') dialog!: ElementRef;

  showDialog(): void {
    this.dialog.nativeElement.style.display = 'flex';
  }

  hideDialog(): void {
    this.dialog.nativeElement.style.display = 'none';
  }
  searchComment(name: string): void {
    if (name != null) {
      const nameNumber = parseInt(name);
      const result = this.commentService.getCommentById(nameNumber);
      this.comments = result ? [result] : [];
    }
  }
}
