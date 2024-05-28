import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { FileService } from '../service/file.service';
import { File } from '../models/file.model';

@Component({
  selector: 'app-fichier-list-specific',
  templateUrl: './fichier-list-specific.component.html',
  styleUrl: './fichier-list-specific.component.css'
})
export class FichierListSpecificComponent implements OnInit {
  files: File[] = [];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private fileService: FileService
  ) { }
  
  ngOnInit(): void {
    this.getFiles();
  }

  getFiles(): void {
    const path = this.router.url.split('/');
    const id = Number(path[path.length - 1]);
    if (path.includes('group')) {
      this.files = this.fileService.getFilesByGroupId(id);
    } else if (path.includes('user')) {
      this.files = this.fileService.getFilesByUserId(id);
    }
  }

  @ViewChild('dialog') dialog!: ElementRef;

  showDialog(): void {
    this.dialog.nativeElement.style.display = 'flex';
  }

  hideDialog(): void {
    this.dialog.nativeElement.style.display = 'none';
  }

  searchFile(name: string): void {
    if (name != null) {
      const nameNumber = parseInt(name);
      const result = this.fileService.getFileById(nameNumber);
      this.files = result ? [result] : [];
    }
  }
}
