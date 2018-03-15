import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { User } from '../user';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html'
})
export class MessageComponent implements OnInit {
  //TODO: use UserService
  users: User[] = [
    { "id" : 1, "userName" : "sanne" },
    { "id" : 2, "userName" : "andrea" },
    { "id" : 3, "userName" : "guillaume" },
    { "id" : 4, "userName" : "davide" },
    { "id" : 5, "userName" : "emmanuel" },
    { "id" : 6, "userName" : "yoann" },
    { "id" : 7, "userName" : "fabio" },
    { "id" : 8, "userName" : "gunnar" }
  ];

  userName: string;

  constructor( private route: ActivatedRoute ) { }

  ngOnInit() {
    this.refreshUserName();
  }

  refreshUserName(): void {
    this.userName = this.route.snapshot.paramMap.get('userName');
  }

  onSelect(user: User): void {
    this.userName = user.userName;
  }

}
