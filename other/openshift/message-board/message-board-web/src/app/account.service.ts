import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {catchError, map, tap} from 'rxjs/operators';
import {of} from 'rxjs/observable/of';
import 'rxjs/add/operator/map';

import {User} from './user';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class AccountService {
  private loginUrl = 'users';

  private user: User;

  constructor(
    private http: HttpClient) {

  }

  createAccount(user: User): Observable<User> {
    const url = `http://web-message-board.192.168.42.57.nip.io/users}`;
    console.log(url);
    return this.http.post<User>(url, user, httpOptions).map(
      newUser => {
        console.log('user ->  ' + newUser.id);
        if (newUser.id == null) {
          console.log('user is empty');
          Observable.throw('Username or password is incorrect');

        } else {
          console.log('a new user ');
          localStorage.setItem('currentUser', JSON.stringify(user));
        }
        return newUser;
      }
    );
  }
}