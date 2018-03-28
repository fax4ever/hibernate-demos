import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';

import { Message } from './message';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class MessageService {

  private username: string;

  constructor( private http: HttpClient ) { }

  findMessagesByUsername(term: string): Observable<Message[]> {
    if (!term || !term.trim()) {
      // if not search term, return empty message array.
      return of([]);
    }
    this.username = term;
    return this.http.get<Message[]>(`message-service/messages?username=${term}`);
  }

  postMessage(body: string): Observable<any> {
    const message = new Message();
    message.username = this.username;
    message.body = body;
    return this.http.post<Message>('message-service/messages', message, httpOptions);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<Message>(`message-service/messages/${id}`, httpOptions);
  }

}
