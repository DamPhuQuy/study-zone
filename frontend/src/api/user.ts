import api from './axiosInstance';
import type {
  ApiResponse,
  UserDetailedResponseDTO,
  UserPointsResponseDTO,
  UserTimesResponseDTO,
} from '../types';

export const userApi = {
  getMe: () =>
    api.get<ApiResponse<UserDetailedResponseDTO>>('/v1/users/me').then((r) => r.data),
  getPoints: () =>
    api.get<ApiResponse<UserPointsResponseDTO>>('/v1/users/me/points').then((r) => r.data),
  getTimes: () =>
    api.get<ApiResponse<UserTimesResponseDTO>>('/v1/users/me/times').then((r) => r.data),
  updateProfile: (data: { username?: string; avatarUrl?: string }) =>
    api.put('/v1/users/profile', data),
};
