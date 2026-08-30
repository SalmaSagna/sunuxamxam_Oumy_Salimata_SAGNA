import { ComponentFixture, TestBed } from '@angular/core/testing';
import { GestionConcours } from './gestion-concours';

describe('GestionConcours', () => {
  let component: GestionConcours;
  let fixture: ComponentFixture<GestionConcours>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionConcours],
    }).compileComponents();

    fixture = TestBed.createComponent(GestionConcours);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
