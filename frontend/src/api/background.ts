import type {
  ApiResponse,
  BackgroundSavedResponseDTO,
  BackgroundUnlockedResponseDTO,
} from "../types";
import api from "./axiosInstance";

export const backgroundApi = {
  getSaved: () =>
    api
      .get<
        ApiResponse<BackgroundSavedResponseDTO[]>
      >("/v1/backgrounds/me/saved-backgrounds")
      .then((r) => r.data),
  getUnlocked: () =>
    api
      .get<
        ApiResponse<BackgroundUnlockedResponseDTO[]>
      >("/v1/backgrounds/me/unlocked-backgrounds")
      .then((r) => r.data),
  save: (backgroundId: number) =>
    api
      .post<
        ApiResponse<BackgroundSavedResponseDTO>
      >(`/v1/backgrounds/me/saved-backgrounds/${backgroundId}`)
      .then((r) => r.data),
  unlock: (backgroundId: number) =>
    api
      .post<
        ApiResponse<BackgroundUnlockedResponseDTO>
      >(`/v1/backgrounds/me/unlocked-backgrounds/${backgroundId}`)
      .then((r) => r.data),
  removeSaved: (backgroundId: number) =>
    api.delete(`/v1/backgrounds/me/saved-backgrounds/${backgroundId}`),
};
