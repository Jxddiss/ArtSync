import { Injectable } from '@angular/core';
import { POSTS } from './mock-post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  getAllPosts() {
    return POSTS;
  }
  getPostByUserId(id: number) {
      return POSTS.find(post => post.utilisateur.id === id);
  }
  getAllPostByUserId(id: number) {
    return POSTS.filter(post => post.utilisateur.id === id);
  }
}
