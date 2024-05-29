import { Component, OnInit } from '@angular/core';
import { ForumService } from '../service/forum.service';
import { Forum } from '../models/forum.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-forum-list',
  templateUrl: './forum-list.component.html',
  styleUrl: './forum-list.component.css'
})
export class ForumListComponent implements OnInit {
  private _forums : Forum[]
  private _allForums : Forum[]
  private _subscriptions : Subscription[] = []
  
  constructor(private forumService : ForumService) {
    this._forums = []
    this._allForums = []
  }

  ngOnInit(){
    this._subscriptions.push(
      this.forumService.getAllForums().subscribe(forums => {
        this._forums = forums
        this._allForums = forums
      })
    )
  }

  ngOnDestroy(){
    this._subscriptions.forEach(subscription => subscription.unsubscribe())
  }

  searchForum(name: string): void {
    const searchResults : Forum[] = this._allForums
    if (!name) {
      this._forums = searchResults
      return;
    }

    this._forums = searchResults.filter((forum : Forum) =>
        forum.titre.toLowerCase().indexOf(name.toLowerCase()) !== -1
    )
  }

  get forums():Forum[]{
    return this._forums
  }
}
