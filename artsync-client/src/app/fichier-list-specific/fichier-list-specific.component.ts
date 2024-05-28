import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { FileService } from '../service/file.service';
import { File } from '../models/file.model';
@Component({
  selector: 'app-fichier-list-specific',
  templateUrl: './fichier-list-specific.component.html',
  styleUrl: './fichier-list-specific.component.css'
})
export class FichierListSpecificComponent implements OnInit {
  files: File[] = [];
  @ViewChild('dialog') dialog!: ElementRef;
  constructor(private fileService: FileService) {}
  
  ngOnInit(): void {
    this.files = this.fileService.getAllFiles();
  }

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
