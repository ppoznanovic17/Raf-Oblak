import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Machine, MachineCreateRequest, MachineSearchRequest } from 'src/models/machine.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MachineService {
  private apiUrl = 'http://localhost:8080/api/machines'; 

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}
  
  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Content-Type': 'application/json'
    });
  }

   searchMachines(searchRequest: MachineSearchRequest = {}): Observable<Machine[]> {
    return this.http.post<Machine[]>(`${this.apiUrl}/search`, searchRequest, { headers: this.getHeaders() });
  }

  createMachine(machine: MachineCreateRequest): Observable<Machine> {
    return this.http.post<Machine>(this.apiUrl, machine, { headers: this.getHeaders() });
  }
}
