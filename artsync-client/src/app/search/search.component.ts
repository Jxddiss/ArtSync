import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent {
  @Output() searchQuery: EventEmitter<string> = new EventEmitter<string>();
  search(name: string): void {
    this.searchQuery.emit(name);
  }
}
