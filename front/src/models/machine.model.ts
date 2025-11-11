export interface Machine {
  id: number;
  name: string;
  type: string | null;
  description: string | null;
  uuid: string;
  status: 'RUNNING' | 'STOPPED';
  active: boolean;
  createdAt: string;
  createdByUserId: number;
  createdByUserEmail: string;
  busy: boolean;
}

export interface MachineCreateRequest {
  name: string;
  type?: string;
  description?: string;
}

export interface MachineSearchRequest {
  name?: string;
  statuses?: string[];
  dateFrom?: string;
  dateTo?: string;
}

export interface ErrorLog {
  id: number;
  timestamp: string;
  machineId: number;
  machineName: string;
  operation: 'START' | 'STOP' | 'RESTART';
  errorMessage: string;
}

export interface ErrorLogCreateRequest {
  machineId: number;
  operationType: string;
  errorMessage: string;
}

export const errorMessages = [
  "Masina nije pronadjena ili je obrisana",
  "Masina je zauzeta",
  "Operacija je vec u toku",
  "Masina mora biti ugasena da bi mogla biti upaljena",
  "Masina mora biti upaljena da bi mogla biti ugasena",
  "Masina mora biti upaljena da bi mogla biti restartovana",
  "Neocekivana greska"
];

export const operationTypes = ["start", "stop", "restart", "destroy"];

