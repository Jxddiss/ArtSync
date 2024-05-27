import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForumListSpecificComponent } from './forum-list-specific.component';

describe('ForumListSpecificComponent', () => {
  let component: ForumListSpecificComponent;
  let fixture: ComponentFixture<ForumListSpecificComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ForumListSpecificComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ForumListSpecificComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
