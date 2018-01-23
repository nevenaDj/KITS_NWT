import { TestBed, inject } from '@angular/core/testing';

import { BillsDataService } from './bills-data.service';

describe('BillsDataService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BillsDataService]
    });
  });

  it('should be created', inject([BillsDataService], (service: BillsDataService) => {
    expect(service).toBeTruthy();
  }));
});
