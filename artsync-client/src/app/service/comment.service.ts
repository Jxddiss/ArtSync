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
    getCommentByUserIds(userId: number) {
        return COMMENTS.filter(comment => comment.user.id === userId);
    }
    getCommentByPostId(postId: number) {
        return COMMENTS.filter(comment => comment.postId === postId);
    }
    getCommentByForumId(forumId: number) {
        return COMMENTS.filter(comment => comment.forumId === forumId);
    }
}