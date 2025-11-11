import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from '../components/login/login.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { MachinesComponent } from '../components/machines/machines.component';
import { UsersComponent } from '../components/users/users.component';
import { MachineErrorsComponent } from '../components/machine-errors/machine-errors.component';
import { CreateMachineComponent } from '../components/create-machine/create-machine.component';
import { CreateUserComponent } from '../components/create-user/create-user.component';
import { AuthInterceptor } from './auth.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MachinesComponent,
    UsersComponent,
    MachineErrorsComponent,
    CreateMachineComponent,
    CreateUserComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
