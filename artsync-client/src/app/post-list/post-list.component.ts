import { Component } from '@angular/core';
import { Post } from '../models/post.model';
import { PostService } from '../service/post.service';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css'
})
export class PostListComponent {
  private _posts:Post[]
  private _allPosts:Post[]

  constructor(private postService : PostService){
    this._posts = []
    this._allPosts = []
  }

  ngOnInit(){
    this._posts = this.postService.getAllPosts()
    this._allPosts = this._posts
  }

  searchPost(name: string): void {
    const searchResults : Post[] = this._allPosts
    if (!name) {
      this._posts = searchResults
      return;
    }

    this._posts = this._allPosts.filter((post:Post)=>
      post.titre.toLowerCase().indexOf(name.toLowerCase()) != -1
    )
  }

  get posts():Post[]{
    return this._posts
  }
}
