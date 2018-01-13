import { TestBed, inject } from '@angular/core/testing';

import { GlitchTypeService } from './glitch-type.service';

describe('GlitchTypeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GlitchTypeService]
    });
  });

  it('should be created', inject([GlitchTypeService], (service: GlitchTypeService) => {
    expect(service).toBeTruthy();
  }));
});
