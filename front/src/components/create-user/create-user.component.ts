import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/services/user/user.service';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.scss']
})
export class CreateUserComponent implements OnInit{
  isEditMode = false;
  userId: number | null = null;
  errorMessage = '';
  
  user: any = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    permissions: []
  };

  allPermissions = [
    'can_create_users',
    'can_read_users',
    'can_update_users',
    'can_delete_users',
    'can_search_machines',
    'can_start_machines',
    'can_stop_machines',
    'can_restart_machines',
    'can_create_machines',
    'can_destroy_machines'
  ];

  constructor(
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.userId = +id;
      this.loadUser();
    }
  }

  loadUser(): void {
    if (this.userId) {
      this.userService.getUserById(this.userId).subscribe({
        next: (data) => {
          this.user = { ...data };
        },
        error: (err) => this.errorMessage = 'Greska pri ucitavanju korisnika'
      });
    }
  }

  togglePermission(perm: string): void {
    const index = this.user.permissions.indexOf(perm);
    if (index > -1) {
      this.user.permissions.splice(index, 1);
    } else {
      this.user.permissions.push(perm);
    }
  }

  createUser(): void {
    if (this.isEditMode && this.userId) {
      const updateData = {
        firstName: this.user.firstName,
        lastName: this.user.lastName,
        email: this.user.email,
        permissions: this.user.permissions
      };
      this.userService.updateUser(this.userId, updateData).subscribe({
        next: () => this.router.navigate(['/users']),
        error: (err) => this.errorMessage = 'Greska pri azuriranju korisnika'
      });
    } else {
      this.userService.createUser(this.user).subscribe({
        next: () => this.router.navigate(['/users']),
        error: (err) => this.errorMessage = 'Greska pri kreiranju korisnika'
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/users']);
  }

  formatPermission(perm: string): string {
    return perm.replace('can_', '').replace(/_/g, ' ');
  }
}
