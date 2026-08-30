import { ComponentFixture, TestBed } from '@angular/core/testing';
import { GestionCandidatures } from './gestion-candidatures';

describe('GestionCandidatures', () => {
  let component: GestionCandidatures;
  let fixture: ComponentFixture<GestionCandidatures>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionCandidatures],
    }).compileComponents();

    fixture = TestBed.createComponent(GestionCandidatures);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
