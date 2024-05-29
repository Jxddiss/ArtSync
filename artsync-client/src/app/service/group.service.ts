import { Injectable } from '@angular/core';
import { environment } from '../constants/environment.constant';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Group } from '../models/group.model';
import { Utilisateur } from '../models/utilisateur.model';

@Injectable({
    providedIn: 'root'
})

export class GroupService {
    private host_url = environment.apiUrl;
    private _membresGroupe : Utilisateur[] = [];
    constructor(private http: HttpClient) {}
    getAllGroups() : Observable<Group[]> {
        return this.http.get<Group[]>(this.host_url + '/api/groups');
    }
    getGroupById(id: number) {
        return this.http.get<Group>(this.host_url + '/api/groups/' + id);
    }
    get membresGroupe(): Utilisateur[] {
        return this._membresGroupe;
    }
    set membresGroupe(membresGroupe: Utilisateur[]) {
        this._membresGroupe = membresGroupe;
    }

}