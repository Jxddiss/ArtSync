import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../constants/environment.constant';
import { Post } from '../models/post.model';
import { Observable } from 'rxjs';
import { Comment } from '../models/comment.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private host_url = environment.apiUrl;
  constructor(private http: HttpClient) {}

  getAllPosts() {
    return this.http.get<Post[]>(this.host_url + '/api/posts');
  }

  getPostById(id: number) {
    return this.http.get<Post>(this.host_url + '/api/posts/'+id);
  }

  // getPostByUserId(id: number) {
  //   return POSTS.find(post => post.utilisateur.id === id);
  // }
  getAllPostByUserId(id: number) {
    return this.http.get<Post[]>(this.host_url + '/api/users/posts/' + id);
  }
  getCommentaireByPostId(id: number) : Observable<Comment[]> {
    return this.http.get<Comment[]>(this.host_url + '/api/posts/commentaires/' + id);
  }

  deletePost(id: number) {
    return this.http.delete<any>(this.host_url + "/api/admin/posts/delete", { params: { 'postId': id } });
  }
}
