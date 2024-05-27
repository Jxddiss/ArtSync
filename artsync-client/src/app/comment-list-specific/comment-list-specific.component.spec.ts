import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentListSpecificComponent } from './comment-list-specific.component';

describe('CommentListSpecificComponent', () => {
  let component: CommentListSpecificComponent;
  let fixture: ComponentFixture<CommentListSpecificComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CommentListSpecificComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CommentListSpecificComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
