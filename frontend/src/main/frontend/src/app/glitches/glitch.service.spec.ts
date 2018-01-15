import { TestBed, inject } from '@angular/core/testing';

import { GlitchService } from './glitch.service';

describe('GlitchService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GlitchService]
    });
  });

  it('should be created', inject([GlitchService], (service: GlitchService) => {
    expect(service).toBeTruthy();
  }));
});
