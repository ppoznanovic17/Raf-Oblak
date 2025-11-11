import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  errorMessage = '';
  credentials = { email: '', password: '' };

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login(): void {
    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        if (response.permissions.length === 0) {
          this.errorMessage = 'Nemate nijednu dozvolu!';
        } else {
          console.log('machineeees')
          this.router.navigate(['/machines']);
        }
      },
      error: (err) => {
        this.errorMessage = 'Neispravni kredencijali!';
      }
    });
  }
}
