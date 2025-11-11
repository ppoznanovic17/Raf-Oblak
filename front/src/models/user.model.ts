export interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  permissions: string[];
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  userId: number;
  email: string;
  firstName: string;
  lastName: string;
  permissions: string[];
}

export interface UserCreateRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  permissions: string[];
}

export interface UserUpdateRequest {
  firstName: string;
  lastName: string;
  email: string;
  permissions: string[];
}