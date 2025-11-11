import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { ErrorLogCreateRequest, ErrorLog } from 'src/models/machine.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorLogService {

  private apiUrl = 'http://localhost:8080/api/errors';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  
  getErrorLogs(): Observable<ErrorLog[]> {
    return this.http.get<ErrorLog[]>(this.apiUrl);
  }

  createErrorLog(errorLog: ErrorLogCreateRequest): Observable<ErrorLog> {
      return this.http.post<ErrorLog>(this.apiUrl, errorLog);
    }
}
