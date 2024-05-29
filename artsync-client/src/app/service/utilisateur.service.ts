import { Injectable } from '@angular/core';
import { environment } from '../constants/environment.constant';
import { HttpClient } from '@angular/common/http';
import { Utilisateur } from '../models/utilisateur.model';
import { Observable } from 'rxjs';
import { Group } from '../models/group.model';
import { Comment } from '../models/comment.model';

@Injectable({
  providedIn: 'root'
})
export class UtilisateurService {
  private host_url = environment.apiUrl;
  constructor(private http: HttpClient) {}
  getAllUsers() : Observable<Utilisateur[]> {
    return this.http.get<Utilisateur[]>(this.host_url + '/api/users');
  }
  getUserById(id: number) {
    return this.http.get<Utilisateur>(this.host_url + '/api/users/' + id);
  }
  getGroupByUserId(id: number) {
    return this.http.get<Group[]>(this.host_url + '/api/users/groups/' + id);
  } 
  getCommentByUserId(id: number) {
    return this.http.get<Comment[]>(this.host_url + '/api/users/commentaires/' + id);
  }
  updateUser(user: Utilisateur) {
    return this.http.put<Utilisateur>(this.host_url + '/api/users/update', user);
  }
}
