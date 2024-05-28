import { Component, ElementRef, ViewChild, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { GroupService } from '../service/group.service';
import { Group } from '../models/group.model';
import { Utilisateur } from '../models/utilisateur.model';

@Component({
  selector: 'app-group-preview',
  templateUrl: './group-preview.component.html',
  styleUrl: './group-preview.component.css'
})
export class GroupPreviewComponent implements OnInit{
  group : Group | undefined;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private groupService: GroupService
  ) { }

  ngOnInit(): void {
    this.getGroup();
  }

  getGroup(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.group = this.groupService.getGroupById(id);
  }

  @ViewChild('dialog') dialog!: ElementRef;

  showDialog(): void {
    this.dialog.nativeElement.style.display = 'flex';
  }

  hideDialog(): void {
    this.dialog.nativeElement.style.display = 'none';
  }

  isActive(url: string): boolean {
    return this.router.url === url;
  }
  
}
