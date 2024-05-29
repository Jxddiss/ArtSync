import { Injectable } from '@angular/core';
import { environment } from '../constants/environment.constant';
import { HttpClient } from '@angular/common/http';
import { Forum } from '../models/forum.model';
import { Observable } from 'rxjs';
import { Utilisateur } from '../models/utilisateur.model';
import {Comment } from '../models/comment.model';

@Injectable({
    providedIn: 'root'
})
export class ForumService {
    private host_url = environment.apiUrl;
    constructor(private http: HttpClient) {}
    getAllForums() : Observable<Forum[]> {
        return this.http.get<Forum[]>(this.host_url + '/api/forums');
    }
    getForumById(id: number) {
        return this.http.get<Forum>(this.host_url + '/api/forums/' + id);
    }
    getCommentaireByForumId(id: number) : Observable<Comment[]> {
        return this.http.get<Comment[]>(this.host_url + '/api/forums/commentaires/' + id);
    }
    getAllForumByUtilisateurId(id: number) : Observable<Forum[]> {
        return this.http.get<Forum[]>(this.host_url + '/api/users/forums/' + id);
    }

    deleteForum(id: number) {
        return this.http.delete<any>(this.host_url + "/api/admin/forums/delete", { params: { 'forumId': id } });
    }
}