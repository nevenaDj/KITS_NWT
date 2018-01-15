import { Injectable, Injector } from '@angular/core';
import {HttpInterceptor} from '@angular/common/http';
import { HttpRequest } from '@angular/common/http';
import { HttpHandler } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { HttpEvent } from '@angular/common/http';

@Injectable()
export class TokenInterceptorService implements HttpInterceptor {

  constructor(private injector: Injector) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>{
    request = request.clone({
      setHeaders: {
        'X-Auth-Token': `${localStorage.getItem('token')}`
      }
    });

    return next.handle(request);
  }

}
