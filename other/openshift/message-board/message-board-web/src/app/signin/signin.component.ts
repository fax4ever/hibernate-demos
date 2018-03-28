import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';

import {AccountService} from '../account.service';
import {User} from '../user';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  user: User;

  constructor(private accountService: AccountService,
    private route: ActivatedRoute,
    private router: Router ) {}

  ngOnInit() {
  }

  createAccount(): void {
    this.accountService.createAccount(this.user);
  }

}
