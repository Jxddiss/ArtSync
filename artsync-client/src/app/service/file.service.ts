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
    getFilesByGroupId(groupId: number) {
        return FILES.filter(file => file.groupId === groupId);
    }
    getFilesByUserId(userId: number) {
        return FILES.filter(file => file.user.id === userId);
    }
}