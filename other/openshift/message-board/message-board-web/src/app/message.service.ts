import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';

import { Message } from './message';

@Injectable()
export class MessageService {

  constructor( private http: HttpClient ) { }

  /* GET heroes whose name contains search term */
  findMessagesByUsername(term: string): Observable<Message[]> {
    if (!term.trim()) {
      // if not search term, return empty message array.
      return of([]);
    }
    return this.http.get<Message[]>(`http://localhost:8080/message-service/messages?username={term}`);
  }

}
