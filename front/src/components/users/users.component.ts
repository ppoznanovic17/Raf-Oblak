import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/models/user.model';
import { AuthService } from 'src/services/auth/auth.service';
import { UserService } from 'src/services/user/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  users: User[] = [];
  errorMessage = '';

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.hasPermission('can_read_users')) {
      this.loadUsers();
    }
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (data) => this.users = data,
      error: (err) => this.errorMessage = 'Greska pri ucitavanju korisnika'
    });
  }

  hasPermission(permission: string): boolean {
    console.log(permission + ' --- ' + this.authService.hasPermission(permission));
    return this.authService.hasPermission(permission);
  }

  deleteUser(id: number): void {
    if (confirm('Da li ste sigurni?')) {
      this.userService.deleteUser(id).subscribe({
        next: () => this.loadUsers(),
        error: (err) => this.errorMessage = 'Greska pri brisanju korisnika'
      });
    }
  }

  formatPermission(perm: string): string {
    return perm.replace('can_', '').replace(/_/g, ' ');
  }
}
