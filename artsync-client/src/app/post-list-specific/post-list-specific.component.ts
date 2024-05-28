import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { PostService } from '../service/post.service';
import { Post } from '../models/post.model';

@Component({
  selector: 'app-post-list-specific',
  templateUrl: './post-list-specific.component.html',
  styleUrl: './post-list-specific.component.css'
})
export class PostListSpecificComponent implements OnInit{
  posts : Post[] = [];

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
    this.posts = this.postService.getAllPostByUserId(id);
  }

  searchPost(name: string): void {
    if (!name) {
      //Ajouter la methode
      return;
    }
  }
}
