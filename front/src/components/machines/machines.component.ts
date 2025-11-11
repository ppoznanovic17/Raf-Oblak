import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Machine, MachineSearchRequest, operationTypes, errorMessages, ErrorLogCreateRequest } from 'src/models/machine.model';
import { AuthService } from 'src/services/auth/auth.service';
import { ErrorLogService } from 'src/services/errorLog/error-log.service';
import { MachineService } from 'src/services/machine/machine.service';
@Component({
  selector: 'app-machines',
  templateUrl: './machines.component.html',
  styleUrls: ['./machines.component.scss']
})
export class MachinesComponent implements OnInit {
  machines: Machine[] = [];
  searchCriteria: MachineSearchRequest = {};
  selectedStatus = '';
  successMessage = '';
  errorMessage = '';
  private stompClient: any;

  constructor(
    private machineService: MachineService,
    private authService: AuthService,
    private errorLogService: ErrorLogService,

    private router: Router
  ) { }



  ngOnInit(): void {
    this.loadMachines();
  }

  loadMachines(): void {
    this.machineService.searchMachines(this.searchCriteria).subscribe({
      next: (data) => this.machines = data,
      error: (err) => this.errorMessage = 'Greska pri ucitavanju masina'
    });
  }

  createError(id: number): void {
    const errorLog: ErrorLogCreateRequest = {
      machineId: id, 
      operationType: this.randomItem(operationTypes),
      errorMessage: this.randomItem(errorMessages)
    };
    this.errorLogService.createErrorLog(errorLog).subscribe({
      next: () => this.router.navigate(['/machines']),
      error: (err) => this.errorMessage = 'Greska pri kreiranju masine'
    });
  }

  randomItem<T>(array: T[]): T {
    return array[Math.floor(Math.random() * array.length)];
  }

  search(): void {
    if (this.selectedStatus) {
      this.searchCriteria.statuses = [this.selectedStatus];
    } else {
      this.searchCriteria.statuses = [];
    }
    this.loadMachines();
  }

  startMachine(id: Number): void {
    alert('start ' + id);
  }

  stopMachine(id: Number): void {
    alert('stop ' + id);
  }

  restartMachine(id: Number): void {
    alert('restart ' + id);
  }

  destroyMachine(id: Number): void {
    alert('destroy ' + id);
  }


  hasPermission(permission: string): boolean {
    return this.authService.hasPermission(permission);
  }
  formatDate(dateStr: string): string {
    const date = new Date(dateStr);
    return date.toLocaleDateString('sr-RS');
  }

  hasAnyMachinePermission(): boolean {
    return this.hasPermission('can_start_machines') ||
      this.hasPermission('can_stop_machines') ||
      this.hasPermission('can_restart_machines') ||
      this.hasPermission('can_destroy_machines');
  }

}
