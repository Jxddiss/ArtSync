import { Component, ElementRef, ViewChild, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { ForumService } from '../service/forum.service';
import { Forum } from '../models/forum.model';
import { Utilisateur } from '../models/utilisateur.model';

@Component({
  selector: 'app-forum-preview',
  templateUrl: './forum-preview.component.html',
  styleUrl: './forum-preview.component.css'
})
export class ForumPreviewComponent implements OnInit{
  forum : Forum | undefined;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private forumService: ForumService
  ) { }

  ngOnInit(): void {
    this.getForum();
  }

  getForum(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.forum = this.forumService.getForumById(id);
  }

  @ViewChild('dialog') dialog!: ElementRef;
  showDialog(): void {
    this.dialog.nativeElement.style.display = 'flex';
  }

  hideDialog(): void {
    this.dialog.nativeElement.style.display = 'none';
  }
}
