import { TestBed, inject } from '@angular/core/testing';

import { CommunalProblemService } from './communal-problem.service';

describe('CommunalProblemService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CommunalProblemService]
    });
  });

  it('should be created', inject([CommunalProblemService], (service: CommunalProblemService) => {
    expect(service).toBeTruthy();
  }));
});
