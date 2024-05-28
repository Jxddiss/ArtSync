import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserListSpecificComponent } from './user-list-specific.component';

describe('UserListSpecificComponent', () => {
  let component: UserListSpecificComponent;
  let fixture: ComponentFixture<UserListSpecificComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserListSpecificComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UserListSpecificComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
