import { Injectable } from '@angular/core';
import { COMMENTS } from './mock-comment';

@Injectable({
    providedIn: 'root'
})

export class CommentService {
    constructor() {
        this.getAllComments();
    }
    getAllComments() {
        return COMMENTS;
    }
    getCommentById(id: number) {
        return COMMENTS.find(comment => comment.id === id);
    }
}