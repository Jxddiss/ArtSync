import { Injectable } from '@angular/core';
import { POSTS } from './mock-post';
import { HttpClient } from '@angular/common/http';
import { environment } from '../constants/environment.constant';
import { Post } from '../models/post.model';

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

  getPostByUserId(id: number) {
    return POSTS.find(post => post.utilisateur.id === id);
  }
  getAllPostByUserId(id: number) {
    return POSTS.filter(post => post.utilisateur.id === id);
  }
}
