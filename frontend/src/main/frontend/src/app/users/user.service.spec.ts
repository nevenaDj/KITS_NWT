import { TestBed, inject, getTestBed } from '@angular/core/testing';
import { HttpClientModule, HttpRequest, HttpParams } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { UserService } from './user.service';
import { User } from '../models/user';

describe('UserService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [UserService]
    });

    this.userService = getTestBed().get(UserService);
    this.backend = getTestBed().get(HttpTestingController);
  });

  afterEach(() => {
    this.backend.verify();
  });

  it('should be created', inject([UserService], (service: UserService) => {
    expect(service).toBeTruthy();
  }));

  it('getUsers() should send request', () => {
    this.userService.getUsers(0).then((data: User[]) => expect(data).toBeTruthy());

    this.backend.expectOne((req: HttpRequest<any>) => {
      const params = new HttpParams({fromString: req.urlWithParams});
      return req.url === '/api/users'
          && params.set('page','0').set('size', '15')
          && req.method === 'GET';
    });
  });

  it('getUsers() should return some users', () => {
    let users: User[] = [
      {
        id: 1,
        username: 'user1',
        password: 'user1',
        email: 'user1@gmail.com',
        phoneNo: '123456',
        address: {
          id: 1,
          street: 'street',
          number: '1a',
          city: 'city',
          zipCode: 15000
        }
      },
      {
        id: 2,
        username: 'user2',
        password: 'user2',
        email: 'user2@gmail.com',
        phoneNo: '123456789',
        address: {
          id: 2,
          street: 'street',
          number: '2a',
          city: 'city',
          zipCode: 15000
        }
      },
    ];

    this.userService.getUsers(0).then((data: User[]) => {
      expect(data).toBeTruthy();
      expect(data.length).toBe(2);
      expect(data).toEqual(users);

    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      const params = new HttpParams({fromString: req.urlWithParams});
      return req.url === '/api/users'
          && params.set('page','0').set('size', '15')
          && req.method === 'GET'
      })
      .flush(users, { status: 200, statusText: 'OK'});
  });

  
  it('getUsersCount() should query url and return number of users', () => {
    let count: number = 2;

    this.userService.getUsersCount().then(data => {
      expect(data).toBeTruthy();
      expect(data).toEqual(2);
    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/users/count'
          && req.method === 'GET'
    })
      .flush(count, {status: 200, statusText: 'OK'});
  });

  it('getCurrentUser() should query url and get a user', () => {
    let user: User = new User( {
      id: 1,
      username: 'user',
      password: 'user',
      email: 'user@gmail.com',
      phoneNo: '123456789'
    });

    this.userService.getCurrentUser().then((data:User) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.username).toEqual('user');
      expect(data.email).toEqual('user@gmail.com');
      expect(data.phoneNo).toEqual('123456789');
    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/users/me'
          && req.method === 'GET'
    }).flush(user, {status: 200, statusText: 'OK'});
  });

  it('registration() should query url and save a user', () => {
    let user: User = new User( {
      username: 'user',
      password: 'user',
      email: 'user@gmail.com',
      phoneNo: '123456789'
    });

    this.userService.registration(user).then((data: User) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.username).toEqual('user');
      expect(data.email).toEqual('user@gmail.com');
      expect(data.phoneNo).toEqual('123456789');

    });

    user.id = 1;

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/register'
          && req.method === 'POST'
    })
      .flush(user, {status: 201, statusText: 'CREATED'});
  });


  it('updateUser() should query url and update a user', () => {
    let user: User = new User( {
      id: 1,
      username: 'user',
      password: 'user',
      email: 'user@gmail.com',
      phoneNo: '123456789'
    });

    this.userService.updateUser(user).then((data: User) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.username).toEqual('user');
      expect(data.email).toEqual('user@gmail.com');
      expect(data.phoneNo).toEqual('123456789');

    });


    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/users'
          && req.method === 'PUT'
    })
      .flush(user, {status: 200, statusText: 'OK'});
  });

  it('should call handleError if getCurrentUser produces an error', () => {
    this.userService.getCurrentUser().then((data) => expect(data).toBeFalsy() );

    this.backend.expectOne('/api/users/me').flush(null, {status: 400, statusText:'Bad request'});
  });

});
