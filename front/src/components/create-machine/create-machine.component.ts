import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ErrorLogService } from 'src/services/errorLog/error-log.service';
import { MachineService } from 'src/services/machine/machine.service';

@Component({
  selector: 'app-create-machine',
  templateUrl: './create-machine.component.html',
  styleUrls: ['./create-machine.component.scss']
})
export class CreateMachineComponent {
  machine = {
    name: '',
    type: '',
    description: ''
  };
  errorMessage = '';

  constructor(
    private machineService: MachineService,
    private router: Router
  ) { }

  create(): void {
    this.machineService.createMachine(this.machine).subscribe({
      next: () => this.router.navigate(['/machines']),
      error: (err) => this.errorMessage = 'Greska pri kreiranju masine'
    });
  }

  
  cancel(): void {
    this.router.navigate(['/machines']);
  }
}
