import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';

import {LoginService} from './login.service';
import {MessageService} from './message.service';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {SigninComponent} from './signin/signin.component';
import {BoardDetailComponent} from './board-detail/board-detail.component';
import {PostMessageComponent} from './post-message/post-message.component';
import {MessageComponent} from './message/message.component';

import {HttpClientInMemoryWebApiModule} from 'angular-in-memory-web-api';
import {InMemoryDataService} from './in-memory-data.service';


@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    // The HttpClientInMemoryWebApiModule module intercepts HTTP requests
    // and returns simulated server responses.
    // Remove it when a real server is ready to receive requests.
    HttpClientInMemoryWebApiModule.forRoot(
      InMemoryDataService
    )
  ],
  declarations: [
    AppComponent,
    LoginComponent,
    SigninComponent,
    BoardDetailComponent,
    PostMessageComponent,
    MessageComponent
  ],
  providers: [LoginService, MessageService],
  bootstrap: [AppComponent]
})
export class AppModule {}
