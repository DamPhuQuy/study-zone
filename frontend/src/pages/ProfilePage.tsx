import { BookOpen, Calendar, Clock, Star } from "lucide-react";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import { studyApi } from "../api/study";
import { userApi } from "../api/user";
import type {
  StudySessionResponseDTO,
  UserDetailedResponseDTO,
} from "../types";

function formatDuration(seconds: number) {
  const h = Math.floor(seconds / 3600);
  const m = Math.floor((seconds % 3600) / 60);
  return h > 0 ? `${h}h ${m}m` : `${m}m`;
}

function formatDate(iso: string) {
  return new Date(iso).toLocaleDateString(undefined, {
    month: "short",
    day: "numeric",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
}

function sessionDuration(session: StudySessionResponseDTO) {
  if (!session.endTime) return "Ongoing";
  const diff =
    new Date(session.endTime).getTime() - new Date(session.startTime).getTime();
  return formatDuration(Math.floor(diff / 1000));
}

export default function ProfilePage() {
  const [user, setUser] = useState<UserDetailedResponseDTO | null>(null);
  const [sessions, setSessions] = useState<StudySessionResponseDTO[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([userApi.getMe(), studyApi.getSessions()])
      .then(([userRes, sessionsRes]) => {
        setUser(userRes.data);
        // newest first
        setSessions([...sessionsRes.data].reverse());
      })
      .catch(() => toast.error("Failed to load profile data"))
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-[60vh]">
        <div className="animate-spin size-8 border-4 border-violet-500 border-t-transparent rounded-full" />
      </div>
    );
  }

  if (!user) return null;

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      {/* Header card */}
      <div className="bg-gradient-to-br from-violet-900/40 to-gray-900 border border-white/10 rounded-2xl p-6 flex flex-col sm:flex-row items-center gap-6 mb-8">
        <img
          src={
            user.avatarUrl ??
            `https://api.dicebear.com/7.x/avataaars/svg?seed=${user.username}`
          }
          alt={user.username}
          className="size-20 rounded-full border-4 border-violet-500 bg-gray-800"
        />
        <div className="flex-1 text-center sm:text-left">
          <h1 className="text-2xl font-bold text-white">{user.username}</h1>
          <p className="text-white/50 text-sm">{user.email}</p>
          <span className="inline-block mt-2 px-3 py-0.5 bg-violet-700/50 text-violet-300 text-xs rounded-full capitalize">
            {user.role?.toLowerCase()}
          </span>
        </div>
        <div className="text-center">
          <p className="text-white/40 text-xs mb-1">Member since</p>
          <p className="text-white text-sm">
            {new Date(user.createdAt).toLocaleDateString(undefined, {
              month: "short",
              year: "numeric",
            })}
          </p>
        </div>
      </div>

      {/* Stats row */}
      <div className="grid grid-cols-2 sm:grid-cols-3 gap-4 mb-8">
        {[
          {
            label: "Total Points",
            value: user.totalPoints?.toFixed(0) ?? 0,
            icon: Star,
            color: "text-yellow-400",
            bg: "from-yellow-900/30",
          },
          {
            label: "Total Study Time",
            value: formatDuration(user.totalTimes ?? 0),
            icon: Clock,
            color: "text-blue-400",
            bg: "from-blue-900/30",
          },
          {
            label: "Sessions",
            value: sessions.length,
            icon: BookOpen,
            color: "text-emerald-400",
            bg: "from-emerald-900/30",
          },
        ].map(({ label, value, icon: Icon, color, bg }) => (
          <div
            key={label}
            className={`bg-gradient-to-br ${bg} to-gray-900 border border-white/10 rounded-xl p-4 flex flex-col gap-1`}
          >
            <Icon className={`size-5 ${color}`} />
            <p className="text-2xl font-bold text-white">{value}</p>
            <p className="text-white/50 text-xs">{label}</p>
          </div>
        ))}
      </div>

      {/* Study history */}
      <div>
        <h2 className="text-lg font-semibold text-white mb-4 flex items-center gap-2">
          <Calendar className="size-5 text-violet-400" />
          Study History
        </h2>

        {sessions.length === 0 ? (
          <div className="text-center py-12 text-white/40">
            <BookOpen className="size-10 mx-auto mb-3 opacity-30" />
            <p>No study sessions yet. Start studying!</p>
          </div>
        ) : (
          <div className="flex flex-col gap-3">
            {sessions.map((s) => (
              <div
                key={s.id}
                className="flex items-center justify-between bg-white/5 border border-white/10 rounded-xl px-4 py-3"
              >
                <div>
                  <p className="text-white text-sm font-medium">
                    {formatDate(s.startTime)}
                  </p>
                  <p className="text-white/40 text-xs mt-0.5">
                    Duration: {sessionDuration(s)}
                  </p>
                </div>
                <div className="flex items-center gap-1.5 text-yellow-400 font-semibold">
                  <Star className="size-4 fill-yellow-400" />
                  <span>+{s.pointsEarned?.toFixed(0) ?? 0}</span>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
