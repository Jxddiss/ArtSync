import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../constants/environment.constant';

@Injectable({
    providedIn: 'root'
})

export class CommentService {
    private host_url = environment.apiUrl;
    constructor(private http: HttpClient) {}

    deleteCommentaire(id: number) {
        return this.http.delete<any>(this.host_url + "/api/admin/commentaires/delete", { params: { 'commentaireId': id } });
    }
}