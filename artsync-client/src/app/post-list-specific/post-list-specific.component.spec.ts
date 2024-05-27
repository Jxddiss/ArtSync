import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostListSpecificComponent } from './post-list-specific.component';

describe('PostListSpecificComponent', () => {
  let component: PostListSpecificComponent;
  let fixture: ComponentFixture<PostListSpecificComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PostListSpecificComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PostListSpecificComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
