import { Injectable } from '@angular/core';
import { GROUPS } from './mock-group';

@Injectable({
    providedIn: 'root'
})

export class GroupService {
    constructor() {
        this.getAllGroups();
    }
    getAllGroups() {
        return GROUPS;
    }
    getGroupByTitle(title: string) {
        return GROUPS.find(group => group.titre === title);
    }
    getGroupById(id: number) {
        return GROUPS.find(group => group.id === id);
    }
}