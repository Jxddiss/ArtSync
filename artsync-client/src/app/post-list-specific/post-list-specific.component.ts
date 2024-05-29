import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { PostService } from '../service/post.service';
import { Post } from '../models/post.model';
import { Subscription } from 'rxjs';
import { environment } from '../constants/environment.constant';

@Component({
  selector: 'app-post-list-specific',
  templateUrl: './post-list-specific.component.html',
  styleUrl: './post-list-specific.component.css'
})
export class PostListSpecificComponent implements OnInit{
  posts : Post[] = [];
  _subscriptions: Subscription[] = [];
  private _BASE_PUBLICATION_PHOTO_PATH : string = environment.apiUrl + '/media/images/post/';
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private postService: PostService
  ) { }

  ngOnInit(): void {
    this.getPosts();
  }

  getPosts(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (this.router.url.includes('user')) {
      this._subscriptions.push(
        this.postService.getAllPostByUserId(id).subscribe(
          posts => {
            this.posts = posts;
          }
        )
      );
    }
  }

  ngOnDestroy(): void {
    this._subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  get BASE_PUBLICATION_PHOTO_PATH(): string {
    return this._BASE_PUBLICATION_PHOTO_PATH;
  }
}
