import { Injectable } from '@angular/core';
import { FORUMS } from './mock-forum';

@Injectable({
    providedIn: 'root'
})

export class ForumService {
    constructor() {
        this.getAllForums();
    }
    getAllForums() {
        return FORUMS;
    }
    getForumByTitle(title: string) {
        return FORUMS.find(forum => forum.title === title);
    }
}