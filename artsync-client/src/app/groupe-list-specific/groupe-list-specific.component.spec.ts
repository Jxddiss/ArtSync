import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupeListSpecificComponent } from './groupe-list-specific.component';

describe('GroupeListSpecificComponent', () => {
  let component: GroupeListSpecificComponent;
  let fixture: ComponentFixture<GroupeListSpecificComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GroupeListSpecificComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GroupeListSpecificComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
