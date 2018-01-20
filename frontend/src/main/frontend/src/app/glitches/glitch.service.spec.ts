import { TestBed, inject, getTestBed } from '@angular/core/testing';
import { HttpClientModule, HttpRequest, HttpParams } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { GlitchService } from './glitch.service';
import { Glitch } from '../models/glitch';
import { Comment } from '../models/comment';

describe('GlitchService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [GlitchService]
    });

    this.glitchService = getTestBed().get(GlitchService);
    this.backend = getTestBed().get(HttpTestingController);
  });

  afterEach(() => {
    this.backend.verify();
  });

  it('should be created', inject([GlitchService], (service: GlitchService) => {
    expect(service).toBeTruthy();
  }));

  it('getGlitches() send request', () => {
    this.glitchService.getGlitches(0).then((data: Glitch[]) => expect(data).toBeTruthy());

    this.backend.expectOne((req: HttpRequest<any>) => {
      const params = new HttpParams({fromString: req.urlWithParams});
      return req.url === '/api/glitches'
          && params.set('page','0').set('size', '8')
          && req.method === 'GET';
    });
  });

  it('getGlitches() should return some glitches', () => {
    let glitches: Glitch[] = [
      {
        id: 1,
        dateOfReport: null,
        dateOfRepair: null,
        state: {
          id: 1,
          state: 'REPORTED'
        },
        type:{
          id: 1,
          type: 'VODOVOD'
        },
        apartment: null,
        description: 'description',
        responsiblePerson: null
      }
    ];

    this.glitchService.getGlitches(0).then((data: Glitch[]) => {
      expect(data).toBeTruthy();
      expect(data.length).toBe(1);
      expect(data).toEqual(glitches);

    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      const params = new HttpParams({fromString: req.urlWithParams});
      return req.url === '/api/glitches'
          && params.set('page','0').set('size', '15')
          && req.method === 'GET'
      })
      .flush(glitches, { status: 200, statusText: 'OK'});

  });

  it('getGlitch() should query url and get a glitch', () => {
    let glitch: Glitch = new Glitch({
      id: 1,
      dateOfReport: null,
      dateOfRepair: null,
      state: {
        id: 1,
        state: 'REPORTED'
      },
      type:{
        id: 1,
        type: 'VODOVOD'
      },
      apartment: null,
      description: 'description',
      responsiblePerson: null
    });

    this.glitchService.getGlitch(glitch.id).then((data:Glitch) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.state.id).toEqual(1);
      expect(data.state.state).toEqual('REPORTED');
      expect(data.type.id).toEqual(1);
      expect(data.type.type).toEqual('VODOVOD');
      expect(data.description).toEqual('description');
      expect(data.responsiblePerson).toBeNull();
    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/glitches/${glitch.id}`
          && req.method === 'GET'
    }).flush(glitch, {status: 200, statusText: 'OK'});
  });

  it('addGlitch() should query url and save a glitch', () => {
    let apartmentID: number = 2;
    let glitch: Glitch = new Glitch({
      dateOfReport: null,
      dateOfRepair: null,
      state: {
        id: 1,
        state: 'REPORTED'
      },
      type:{
        id: 1,
        type: 'VODOVOD'
      },
      apartment: null,
      description: 'description',
      responsiblePerson: null
    });

    this.glitchService.addGlitch(2,glitch).then((data: Glitch) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(2);
      expect(data.state.state).toEqual('REPORTED');
      expect(data.type.id).toEqual(1);
      expect(data.type.type).toEqual('VODOVOD');
      expect(data.description).toEqual('description');
      expect(data.responsiblePerson).toBeNull();

    });

    glitch.id = 2;

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/apartments/${apartmentID}/glitches`
          && req.method === 'POST'
    })
      .flush(glitch, {status: 201, statusText: 'CREATED'});
  });

  it('getGlitchesCount() should query url and return number of glitches', () => {
    let count: number = 2;

    this.glitchService.getGlitchesCount().then(data => {
      expect(data).toBeTruthy();
      expect(data).toEqual(2);
    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/glitches/count'
          && req.method === 'GET'
    })
      .flush(count, {status: 200, statusText: 'OK'});

  });

  it('addComment() should query url and save a comment', () => {
    let glitchID: number = 2;
    let comment: Comment = new Comment({
      text: 'text',
      user: null
    });

    this.glitchService.addComment(glitchID,comment).then((data: Comment) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(2);
      expect(data.text).toEqual('text');     

    });

    comment.id = 2;

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/glitches/${glitchID}/comments`
          && req.method === 'POST'
    })
      .flush(comment, {status: 201, statusText: 'CREATED'});
  });

  it('getComments() should query url and get a comments', () => {
    let glitchID: number = 2;
    let comments: Comment[] = [
      {
        id: 1,
        text: 'text',
        user: null
      },
      {
        id: 2,
        text: 'text2',
        user: null
      }
    ];

    this.glitchService.getComments(glitchID).then((data:Comment[]) => {
      expect(data).toBeTruthy();
      expect(data.length).toBe(2);
      expect(data).toEqual(comments);

    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/glitches/${glitchID}/comments`
          && req.method === 'GET'
    }).flush(comments, {status: 200, statusText: 'OK'});
  });

  



  

});
