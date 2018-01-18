import { TestBed, inject, getTestBed } from '@angular/core/testing';
import { HttpClientModule, HttpRequest, HttpParams } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { GlitchTypeService } from './glitch-type.service';
import { GlitchType } from '../models/glitch-type';

describe('GlitchTypeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [GlitchTypeService]
    });

    this.glitchTypeService = getTestBed().get(GlitchTypeService);
    this.backend = getTestBed().get(HttpTestingController);
  });

  afterEach(() => {
    this.backend.verify();
  });

  it('should be created', inject([GlitchTypeService], (service: GlitchTypeService) => {
    expect(service).toBeTruthy();
  }));

  it('getGlitchTpes() should return some glitch types', () => {
    let glitchTypes: GlitchType[] = [
      {
        id: 1,
        type: 'type 1'
      },
      {
        id: 2,
        type: 'type 2'
      },
      {
        id: 3,
        type: 'type 3'
      },
    ];

    this.glitchTypeService.getGlitchTypes().then((data: GlitchType[]) => {
      expect(data).toBeTruthy();
      expect(data.length).toBe(3);
      expect(data).toEqual(glitchTypes);

    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/glitchTypes'
          && req.method === 'GET'
      })
      .flush(glitchTypes, { status: 200, statusText: 'OK'});

  });

  it('addGlitchType() should query url and save a glitchType', () => {
    let glitchType: GlitchType = new GlitchType({
      type: 'new type'
    });

    this.glitchTypeService.addGlitchType(glitchType).then((data: GlitchType) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.type).toEqual('new type');
    });

    glitchType.id = 1;

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/glitchTypes'
          && req.method === 'POST'
    })
      .flush(glitchType, {status: 201, statusText: 'CREATED'});
  });

  it('deleteGlitchType() should query url and delete a glitchType', () => {
    let glitchTypeID = 1;

    this.glitchTypeService.deleteGlitchType(glitchTypeID).then((data)=> expect(data).toBeTruthy());

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/glitchTypes/${glitchTypeID}`
          && req.method === 'DELETE'
    }).flush(null, {status: 200, statusText: 'OK'});

  });

  

  

});
