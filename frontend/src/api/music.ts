import type {
  ApiResponse,
  MusicSavedResponseDTO,
  MusicUnlockedResponseDTO,
} from "../types";
import api from "./axiosInstance";

export const musicApi = {
  getSaved: () =>
    api
      .get<ApiResponse<MusicSavedResponseDTO[]>>("/v1/music/me/saved-music")
      .then((r) => r.data),
  getUnlocked: () =>
    api
      .get<
        ApiResponse<MusicUnlockedResponseDTO[]>
      >("/v1/music/me/unlocked-music")
      .then((r) => r.data),
  save: (musicId: number) =>
    api
      .post<
        ApiResponse<MusicSavedResponseDTO>
      >(`/v1/music/me/saved-music/${musicId}`)
      .then((r) => r.data),
  unlock: (musicId: number) =>
    api
      .post<
        ApiResponse<MusicUnlockedResponseDTO>
      >(`/v1/music/me/unlocked-music/${musicId}`)
      .then((r) => r.data),
  removeSaved: (musicId: number) => api.delete(`/v1/music/me/saved/${musicId}`),
};
