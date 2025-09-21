import { TestBed } from '@angular/core/testing';

import { MainBsService } from './main-bs.service';

describe('MainBsService', () => {
  let service: MainBsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MainBsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
