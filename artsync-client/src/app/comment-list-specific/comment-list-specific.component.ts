import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { CommentService } from '../service/comment.service';
import { Comment } from '../models/comment.model';
@Component({
  selector: 'app-comment-list-specific',
  templateUrl: './comment-list-specific.component.html',
  styleUrl: './comment-list-specific.component.css'
})
export class CommentListSpecificComponent implements OnInit {
  comments: Comment[] = [];
  
  @ViewChild('dialog') dialog!: ElementRef;
  constructor(private commentService: CommentService) {}

  ngOnInit(): void {
    this.comments = this.commentService.getAllComments();
  }

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
