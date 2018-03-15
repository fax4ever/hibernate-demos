import { Component, OnInit, Input } from '@angular/core';

import { Message } from '../message';
import { MessageService }  from '../message.service';

@Component({
  selector: 'app-board-detail',
  templateUrl: './board-detail.component.html'
})
export class BoardDetailComponent implements OnInit {
  @Input() userName: string;
  messages: Message[];

  constructor( private service: MessageService ) { }

  ngOnInit() {
    this.getMessages();
  }

  getMessages(): void {
    this.service.findMessagesByUsername(this.userName)
      .subscribe(messages => this.messages = messages);
  }

}
