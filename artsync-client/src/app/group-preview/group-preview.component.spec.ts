import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupPreviewComponent } from './group-preview.component';

describe('GroupPreviewComponent', () => {
  let component: GroupPreviewComponent;
  let fixture: ComponentFixture<GroupPreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GroupPreviewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GroupPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
