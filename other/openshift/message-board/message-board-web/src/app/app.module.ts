import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';


import { AppComponent } from './app.component';
import { LoginService } from './login.service';
import { LoginComponent } from './login/login.component';
import { SigninComponent } from './signin/signin.component';
import { AppRoutingModule } from './app-routing.module';
import { BoardDetailComponent } from './board-detail/board-detail.component';
import { BoardListComponent } from './board-list/board-list.component';
import { PostMessageComponent } from './post-message/post-message.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SigninComponent,
    BoardDetailComponent,
    BoardListComponent,
    PostMessageComponent
  ],
  imports: [
    BrowserModule,
     FormsModule,
     AppRoutingModule
  ],
  providers: [LoginService],
  bootstrap: [AppComponent]
})
export class AppModule { }
