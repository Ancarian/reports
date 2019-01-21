import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {catchError} from 'rxjs/operators';
import {Observable, throwError} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ApiServiceService {
  constructor(private http: HttpClient, private router: Router) {
  }

  get(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http.get(`${environment.api_url}${path}`, {params});
  }

  put(path: string, body: Object = {}): Observable<any> {
    return this.http.put(
      `${environment.api_url}${path}`,
      JSON.stringify(body));
  }

  post(path: string, body: Object = {}): Observable<any> {
    return this.http.post(
      `${environment.api_url}${path}`,
      JSON.stringify(body)
    );
  }

  delete(path): Observable<any> {
    return this.http.delete(
      `${environment.api_url}${path}`
    );
  }
}
