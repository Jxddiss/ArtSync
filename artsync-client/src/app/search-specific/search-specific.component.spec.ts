import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchSpecificComponent } from './search-specific.component';

describe('SearchSpecificComponent', () => {
  let component: SearchSpecificComponent;
  let fixture: ComponentFixture<SearchSpecificComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SearchSpecificComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SearchSpecificComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
