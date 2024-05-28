import { Injectable } from '@angular/core';
import { FILES } from './mock-file';

@Injectable({
    providedIn: 'root'
})

export class FileService {
    constructor() {
        this.getAllFiles();
    }
    getAllFiles() {
        return FILES;
    }
    getFileById(id: number) {
        return FILES.find(file => file.id === id);
    }
}