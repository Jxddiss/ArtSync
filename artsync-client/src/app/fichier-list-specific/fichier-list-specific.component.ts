import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { GroupService } from '../service/group.service';
import { FileService } from '../service/file.service';
import { File } from '../models/file.model';
import { Subscription } from 'rxjs';
import { environment } from '../constants/environment.constant';

@Component({
  selector: 'app-fichier-list-specific',
  templateUrl: './fichier-list-specific.component.html',
  styleUrl: './fichier-list-specific.component.css'
})
export class FichierListSpecificComponent implements OnInit {
  _files: File[] = [];
  private _subscriptions: Subscription[] = [];
  private _BASE_FILE_PATH_PROJET: string = environment.apiUrl + '/media/fichier/groupe/';
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private fileService: FileService,
    private groupService: GroupService
  ) { }
  
  ngOnInit(): void {
    this.getFiles();
  }

  getFiles(): void {
    const path = this.router.url.split('/');
    const id = Number(path[path.length - 1]);
    if (path.includes('group')) {
      this._subscriptions.push(
        this.groupService.getGroupById(id).subscribe(
          group => {
            this._files = group.fichiers;
          }
        )
      );
    } else if (path.includes('user')) {
      this._subscriptions.push(
        this.fileService.getFileByUserId(id).subscribe(
          files => {
            this._files = files;
          }
        )
      );
    }
  }

  @ViewChild('dialog') dialog!: ElementRef;

  showDialog(): void {
    this.dialog.nativeElement.style.display = 'flex';
  }

  hideDialog(): void {
    this.dialog.nativeElement.style.display = 'none';
  }
  get BASE_FILE_PATH_PROJET(): string {
    return this._BASE_FILE_PATH_PROJET;
  }
}
