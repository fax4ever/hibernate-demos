import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {catchError, map, tap} from 'rxjs/operators';
import {of} from 'rxjs/observable/of';


import {User} from './user';

@Injectable()
export class LoginService {

  private loginUrl = 'api/login/';


  constructor(
    private http: HttpClient) {}

  login(userName: String) {
    return this.http.post<User>(this.loginUrl, {userName: userName})
      .pipe(
      tap(user => {
        if (user) {
          localStorage.setItem('currentUser', JSON.stringify(user));
        }
      }),
      catchError(this.handleError())
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
//      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }

}
