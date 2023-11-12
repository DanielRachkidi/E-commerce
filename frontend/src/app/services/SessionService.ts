// session.service.ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private readonly TOKEN_KEY = 'token';
  private readonly USER_ID_KEY = 'userId';

  constructor() { }

  getToken(): string {
    // @ts-ignore
    return localStorage.getItem(this.TOKEN_KEY);
  }

  getUserId(): number {
    const userId = localStorage.getItem(this.USER_ID_KEY);
    return userId ? parseInt(userId, 10) : 0;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  clearSession(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_ID_KEY);
  }
}

