import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateMachineComponent } from 'src/components/create-machine/create-machine.component';
import { CreateUserComponent } from 'src/components/create-user/create-user.component';
import { LoginComponent } from 'src/components/login/login.component';
import { MachineErrorsComponent } from 'src/components/machine-errors/machine-errors.component';
import { MachinesComponent } from 'src/components/machines/machines.component';
import { UsersComponent } from 'src/components/users/users.component';
import { authGuard } from './auth.guard';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent, canActivate: [authGuard]},
  { path: 'users', component: UsersComponent, canActivate: [authGuard]},
  { path: 'machines', component: MachinesComponent, canActivate: [authGuard] },
  { path: 'errors', component: MachineErrorsComponent, canActivate: [authGuard]},
  { path: 'machines/new', component: CreateMachineComponent, canActivate: [authGuard]},
  { path: 'users/new', component: CreateUserComponent, canActivate: [authGuard]},
  { path: 'users/:id/edit', component: CreateUserComponent, canActivate: [authGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
