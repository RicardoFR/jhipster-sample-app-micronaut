import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class JhiConfigurationService {
  constructor(private http: HttpClient) {}

  get(): Observable<any> {
    return this.http.get(SERVER_API_URL + 'management/configprops', { observe: 'body' });
  }
}
