import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-search-specific',
  templateUrl: './search-specific.component.html',
  styleUrl: './search-specific.component.css'
})
export class SearchSpecificComponent {
  @Output() searchQuery: EventEmitter<string> = new EventEmitter<string>();
  search(name: string): void {
    this.searchQuery.emit(name);
  }
}
