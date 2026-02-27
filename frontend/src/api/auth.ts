import type {
  ApiResponse,
  AuthResponseDTO,
  LoginRequestDTO,
  RegisterRequestDTO,
} from "../types";
import api from "./axiosInstance";

export const authApi = {
  login: (data: LoginRequestDTO) =>
    api
      .post<ApiResponse<AuthResponseDTO>>("/auth/login", data)
      .then((r) => r.data),
  register: (data: RegisterRequestDTO) =>
    api
      .post<ApiResponse<AuthResponseDTO>>("/auth/register", data)
      .then((r) => r.data),
};
