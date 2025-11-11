import { Component, OnInit } from '@angular/core';
import { ErrorLog } from 'src/models/machine.model';
import { ErrorLogService } from 'src/services/errorLog/error-log.service';

@Component({
  selector: 'app-machine-errors',
  templateUrl: './machine-errors.component.html',
  styleUrls: ['./machine-errors.component.scss']
})
export class MachineErrorsComponent implements OnInit {
  errors: ErrorLog[] = [];
  errorMessage = '';

  constructor(private errorLogService: ErrorLogService) {}

  ngOnInit(): void {
    this.loadErrors();
  }

  loadErrors(): void {
    this.errorLogService.getErrorLogs().subscribe({
      next: (data) => this.errors = data,
      error: (err) => this.errorMessage = 'Greska pri ucitavanju istorije gresaka'
    });
  }

  formatDateTime(dateStr: string): string {
    const date = new Date(dateStr);
    return date.toLocaleString('sr-RS');
  }
}
