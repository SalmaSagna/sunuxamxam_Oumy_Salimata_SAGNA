import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';
import { candidatGuard } from './candidat-guard';

describe('candidatGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) =>
    TestBed.runInInjectionContext(() => candidatGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
