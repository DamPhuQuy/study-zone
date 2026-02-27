// ─── Auth ────────────────────────────────────────────────────────────────────
export interface AuthResponseDTO {
  token: string;
  username: string;
  email: string;
  role: string;
}

export interface LoginRequestDTO {
  username: string;
  password: string;
}

export interface RegisterRequestDTO {
  username: string;
  email: string;
  password: string;
}

// ─── User ────────────────────────────────────────────────────────────────────
export interface UserDetailedResponseDTO {
  id: number;
  username: string;
  email: string;
  totalPoints: number;
  totalTimes: number;
  avatarUrl: string | null;
  role: string;
  createdAt: string;
}

export interface UserPointsResponseDTO {
  totalPoints: number;
}

export interface UserTimesResponseDTO {
  totalTimes: number;
}

// ─── Music ───────────────────────────────────────────────────────────────────
export interface MusicItem {
  id: number;
  title: string;
  musicUrl: string;
  duration: number;
  requiredPoint: number;
  active: boolean;
}

export interface MusicSavedResponseDTO {
  id: number;
  musicId: number;
  title: string;
  musicUrl: string;
  duration: number;
  savedAt: string;
}

export interface MusicUnlockedResponseDTO {
  id: number;
  musicId: number;
  title: string;
  musicUrl: string;
  duration: number;
  pointsUsed: number;
  unlockedAt: string;
}

// ─── Background ──────────────────────────────────────────────────────────────
export interface BackgroundItem {
  id: number;
  name: string;
  imageUrl: string;
  requiredPoint: number;
  active: boolean;
}

export interface BackgroundSavedResponseDTO {
  id: number;
  backgroundId: number;
  name: string;
  imageUrl: string;
  savedAt: string;
}

export interface BackgroundUnlockedResponseDTO {
  id: number;
  backgroundId: number;
  name: string;
  imageUrl: string;
  pointsUsed: number;
  unlockedAt: string;
}

// ─── Study Session ───────────────────────────────────────────────────────────
export interface StudySessionResponseDTO {
  id: number;
  startTime: string;
  endTime: string | null;
  pointsEarned: number;
}

// ─── API Wrapper ─────────────────────────────────────────────────────────────
export interface ApiResponse<T> {
  data: T;
  message: string;
  success: boolean;
}
