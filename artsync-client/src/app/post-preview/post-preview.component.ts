import { Component, ElementRef, ViewChild, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { PostService } from '../service/post.service';
import { Post } from '../models/post.model';
import { Subscription } from 'rxjs';
import { environment } from '../constants/environment.constant';

@Component({
  selector: 'app-post-preview',
  templateUrl: './post-preview.component.html',
  styleUrl: './post-preview.component.css'
})

export class PostPreviewComponent implements OnInit {
  private _post: Post | any;
  private _subscriptions: Subscription[] = []
  private _BASE_PUBLICATION_PHOTO_PATH : string = environment.apiUrl + '/media/images/post/';
  private _BASE_UTILISATEUR_PHOTO_PATH : string = environment.apiUrl + '/media/images/utilisateur/';
  
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private postService: PostService
  ) { }

  ngOnInit(): void {
    this.getPost();
  }

  ngOnDestroy(){
    this._subscriptions.forEach(subscription => subscription.unsubscribe())
  }

  getPost(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this._subscriptions.push(
      this.postService.getPostById(id).subscribe(post =>{
        this._post = post
      })
    )
  }

  get post(){
    return this._post;
  }

  get BASE_PUBLICATION_PHOTO_PATH(){
    return this._BASE_PUBLICATION_PHOTO_PATH
  }

  get BASE_UTILISATEUR_PHOTO_PATH():string{
    return this._BASE_UTILISATEUR_PHOTO_PATH
  }

  @ViewChild('dialog') dialog!: ElementRef;
  showDialog(): void {
    this.dialog.nativeElement.style.display = 'flex';
  }

  hideDialog(): void {
    this.dialog.nativeElement.style.display = 'none';
  }

  onPostDelete(): void {
    this._subscriptions.push(
      this.postService.deletePost(this._post.id).subscribe((response) => {
        if (response.message === 'Success') {
          this.router.navigate(['/generalView', { outlets: { generalView: 'posts' } }]);
        }
      })
    )
    this.hideDialog();
  }
}
