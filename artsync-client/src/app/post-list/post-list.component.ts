import { Component } from '@angular/core';
import { Post } from '../models/post.model';
import { PostService } from '../service/post.service';
import { Subscription } from 'rxjs';
import { environment } from '../constants/environment.constant';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css'
})
export class PostListComponent {
  private _posts:Post[]
  private _allPosts:Post[]
  private subscriptions:Subscription[] = []
  private _BASE_PUBLICATION_PHOTO_PATH : string = environment.apiUrl + '/media/images/post/';

  constructor(private postService : PostService){
    this._posts = []
    this._allPosts = []
  }

  ngOnInit(){
    this.subscriptions.push(
      this.postService.getAllPosts().subscribe(posts =>{
        this._posts = posts
        this._allPosts = posts
      })
    )
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

  get BASE_PUBLICATION_PHOTO_PATH(){
    return this._BASE_PUBLICATION_PHOTO_PATH
  }
}
