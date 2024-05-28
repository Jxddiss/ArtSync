import { Component, OnInit } from '@angular/core';
import { GroupService } from '../service/group.service';
import { Group } from '../models/group.model';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrl: './group-list.component.css'
})
export class GroupListComponent {
  groups: Group[];
  constructor(private groupService: GroupService) {
    this.groups = [];
  }
  ngOnInit(): void {
    this.groups = this.groupService.getAllGroups();
  }
  searchGroup(name: string): void {
    if (name != null) {
      const result = this.groupService.getGroupByTitle(name);
      this.groups = result ? [result] : [];
    }
  }
}
