import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecificViewComponent } from './specific-view.component';

describe('SpecificViewComponent', () => {
  let component: SpecificViewComponent;
  let fixture: ComponentFixture<SpecificViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SpecificViewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SpecificViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
