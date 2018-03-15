import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CommonModule} from '@angular/common';

import {MessageComponent} from './message/message.component';
import {LoginComponent} from './login/login.component';

const routes: Routes = [
  { path: '', redirectTo: '/message', pathMatch: 'full' },
  { path: 'message', component: MessageComponent },
  { path: 'login', component: LoginComponent },
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
