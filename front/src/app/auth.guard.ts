import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');

  // Ako nema token i ruta nije /login → preusmeri
  if (!token && state.url !== '/login') {
    router.navigate(['/login']);
    return false;
  }

  // Ako ima token ili ide na /login → dozvoli
  return true;
};