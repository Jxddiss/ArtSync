import { Injectable } from '@angular/core';
import { environment } from '../constants/environment.constant';
import { HttpClient } from '@angular/common/http';
import { File } from '../models/file.model';

@Injectable({
    providedIn: 'root'
})

export class FileService {
    private host_url = environment.apiUrl;
    constructor(private http:HttpClient) {}
    getFileByUserId(id: number) {
        return this.http.get<File[]>(this.host_url + '/api/users/fichiers/' + id);
    }

}