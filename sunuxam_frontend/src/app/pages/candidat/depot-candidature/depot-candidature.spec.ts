import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DepotCandidature } from './depot-candidature';

describe('DepotCandidature', () => {
  let component: DepotCandidature;
  let fixture: ComponentFixture<DepotCandidature>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DepotCandidature],
    }).compileComponents();

    fixture = TestBed.createComponent(DepotCandidature);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
