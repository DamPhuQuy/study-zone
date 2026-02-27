import type { ApiResponse, StudySessionResponseDTO } from "../types";
import api from "./axiosInstance";

export const studyApi = {
  getSessions: () =>
    api
      .get<ApiResponse<StudySessionResponseDTO[]>>("/v1/study/me")
      .then((r) => r.data),
  startSession: () =>
    api
      .post<ApiResponse<StudySessionResponseDTO>>("/v1/study/me/start")
      .then((r) => r.data),
  endSession: (sessionId: number) =>
    api
      .put<
        ApiResponse<StudySessionResponseDTO>
      >(`/v1/study/me/end/${sessionId}`)
      .then((r) => r.data),
};
