import { TestBed } from '@angular/core/testing';

import { NbgFormatterService } from './nbg-formatter.service';

describe('NbgFormatterService', () => {
  let service: NbgFormatterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NbgFormatterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
