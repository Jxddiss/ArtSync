import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FichierListSpecificComponent } from './fichier-list-specific.component';

describe('FichierListSpecificComponent', () => {
  let component: FichierListSpecificComponent;
  let fixture: ComponentFixture<FichierListSpecificComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FichierListSpecificComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FichierListSpecificComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
