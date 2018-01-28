import { TestBed, inject } from '@angular/core/testing';

import { GlitchDataService } from './glitch-data.service';

describe('GlitchDataService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GlitchDataService]
    });
  });

  it('should be created', inject([GlitchDataService], (service: GlitchDataService) => {
    expect(service).toBeTruthy();
  }));
});
