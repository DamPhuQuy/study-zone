import clsx from "clsx";
import {
  ChevronLeft,
  ChevronRight,
  Image,
  Music,
  Pause,
  Play,
  SkipBack,
  SkipForward,
  Square,
  Star,
  Volume2,
  VolumeX,
} from "lucide-react";
import { useCallback, useEffect, useRef, useState } from "react";
import toast from "react-hot-toast";
import { backgroundApi } from "../api/background";
import { musicApi } from "../api/music";
import { studyApi } from "../api/study";
import type {
  BackgroundSavedResponseDTO,
  MusicSavedResponseDTO,
  StudySessionResponseDTO,
} from "../types";

// ─── Timer ────────────────────────────────────────────────────────────────────
const FOCUS_SECS = 25 * 60;
const BREAK_SECS = 5 * 60;

function formatTime(secs: number) {
  const m = Math.floor(secs / 60)
    .toString()
    .padStart(2, "0");
  const s = (secs % 60).toString().padStart(2, "0");
  return `${m}:${s}`;
}

// ─── Default backgrounds (fallback gradient layers) ───────────────────────────
const GRADIENT_FALLBACK = "linear-gradient(135deg, #0f0c29, #302b63, #24243e)";

export default function StudyPage() {
  // ── Session state ─────────────────────────────────────────────────────────
  const [session, setSession] = useState<StudySessionResponseDTO | null>(null);

  // ── Timer state ───────────────────────────────────────────────────────────
  const [mode, setMode] = useState<"focus" | "break">("focus");
  const [timeLeft, setTimeLeft] = useState(FOCUS_SECS);
  const [timerRunning, setTimerRunning] = useState(false);
  const timerRef = useRef<ReturnType<typeof setInterval> | null>(null);

  // ── Music state ───────────────────────────────────────────────────────────
  const [savedMusic, setSavedMusic] = useState<MusicSavedResponseDTO[]>([]);
  const [musicIdx, setMusicIdx] = useState(0);
  const [musicPlaying, setMusicPlaying] = useState(false);
  const [muted, setMuted] = useState(false);
  const [volume, setVolume] = useState(0.5);
  const [showMusicPanel, setShowMusicPanel] = useState(false);
  const audioRef = useRef<HTMLAudioElement | null>(null);

  // ── Background state ──────────────────────────────────────────────────────
  const [savedBgs, setSavedBgs] = useState<BackgroundSavedResponseDTO[]>([]);
  const [bgIdx, setBgIdx] = useState(0);
  const [showBgPanel, setShowBgPanel] = useState(false);

  // ─── Load music & backgrounds ─────────────────────────────────────────────
  useEffect(() => {
    musicApi
      .getSaved()
      .then((r) => setSavedMusic(r.data))
      .catch(() => {});
    backgroundApi
      .getSaved()
      .then((r) => setSavedBgs(r.data))
      .catch(() => {});
  }, []);

  // ─── Timer tick ───────────────────────────────────────────────────────────
  useEffect(() => {
    if (timerRunning) {
      timerRef.current = setInterval(() => {
        setTimeLeft((t) => {
          if (t <= 1) {
            clearInterval(timerRef.current!);
            setTimerRunning(false);
            const next = mode === "focus" ? "break" : "focus";
            setMode(next);
            setTimeLeft(next === "focus" ? FOCUS_SECS : BREAK_SECS);
            toast.success(
              next === "break"
                ? "🎉 Focus done! Take a break."
                : "⏰ Break over. Back to focus!",
            );
            return 0;
          }
          return t - 1;
        });
      }, 1000);
    } else {
      if (timerRef.current) clearInterval(timerRef.current);
    }
    return () => {
      if (timerRef.current) clearInterval(timerRef.current);
    };
  }, [timerRunning, mode]);

  // ─── Audio control ────────────────────────────────────────────────────────
  useEffect(() => {
    const audio = audioRef.current;
    if (!audio) return;
    audio.volume = muted ? 0 : volume;
  }, [volume, muted]);

  // ─── Handle track change ──────────────────────────────────────────────────
  useEffect(() => {
    const audio = audioRef.current;
    if (!audio || savedMusic.length === 0) return;
    audio.src = savedMusic[musicIdx]?.musicUrl ?? "";
    if (musicPlaying) audio.play().catch(() => {});
  }, [musicIdx, savedMusic]);

  const toggleMusicPlay = () => {
    const audio = audioRef.current;
    if (!audio) return;
    if (musicPlaying) {
      audio.pause();
      setMusicPlaying(false);
    } else {
      if (savedMusic.length === 0) {
        toast.error("Save some music first in the Shop!");
        return;
      }
      audio.src = savedMusic[musicIdx]?.musicUrl ?? "";
      audio
        .play()
        .catch(() =>
          toast("Music preview not available in demo.", { icon: "🎵" }),
        );
      setMusicPlaying(true);
    }
  };

  const prevTrack = () =>
    setMusicIdx((i) => (i === 0 ? savedMusic.length - 1 : i - 1));
  const nextTrack = () => setMusicIdx((i) => (i + 1) % savedMusic.length);

  // ─── Session control ──────────────────────────────────────────────────────
  const startSession = useCallback(async () => {
    try {
      const res = await studyApi.startSession();
      setSession(res.data);
      setTimerRunning(true);
      toast.success("Study session started! 📚");
    } catch {
      toast.error("Failed to start session.");
    }
  }, []);

  const endSession = useCallback(async () => {
    if (!session) return;
    setTimerRunning(false);
    try {
      const res = await studyApi.endSession(session.id);
      toast.success(
        `Session ended! +${res.data.pointsEarned?.toFixed(0)} pts 🎯`,
      );
      setSession(null);
      setMode("focus");
      setTimeLeft(FOCUS_SECS);
    } catch {
      toast.error("Failed to end session.");
    }
  }, [session]);

  // ─── Background ───────────────────────────────────────────────────────────
  const currentBg = savedBgs[bgIdx];
  const bgStyle: React.CSSProperties = currentBg?.imageUrl
    ? {
        backgroundImage: `url(${currentBg.imageUrl})`,
        backgroundSize: "cover",
        backgroundPosition: "center",
      }
    : { background: GRADIENT_FALLBACK };

  // ─── Progress circle ──────────────────────────────────────────────────────
  const total = mode === "focus" ? FOCUS_SECS : BREAK_SECS;
  const progress = (total - timeLeft) / total;
  const circumference = 2 * Math.PI * 110;
  const dash = circumference * (1 - progress);

  return (
    <div
      className="relative flex flex-col items-center justify-center"
      style={{ minHeight: "calc(100vh - 64px)", ...bgStyle }}
    >
      {/* Overlay */}
      <div className="absolute inset-0 bg-black/50 backdrop-blur-[2px]" />

      {/* Audio element */}
      <audio ref={audioRef} loop />

      {/* ── MAIN CONTENT ─────────────────────────────────────────────────── */}
      <div className="relative z-10 flex flex-col items-center gap-6">
        {/* Mode toggle */}
        <div className="flex gap-2 bg-white/10 rounded-full p-1">
          {(["focus", "break"] as const).map((m) => (
            <button
              key={m}
              onClick={() => {
                if (timerRunning) return;
                setMode(m);
                setTimeLeft(m === "focus" ? FOCUS_SECS : BREAK_SECS);
              }}
              className={clsx(
                "px-5 py-1.5 rounded-full text-sm font-medium capitalize transition",
                mode === m
                  ? "bg-violet-600 text-white"
                  : "text-white/70 hover:text-white",
              )}
            >
              {m === "focus" ? "🎯 Focus" : "☕ Break"}
            </button>
          ))}
        </div>

        {/* Timer ring */}
        <div className="relative flex items-center justify-center">
          <svg width={260} height={260} className="-rotate-90">
            <circle
              cx={130}
              cy={130}
              r={110}
              fill="none"
              stroke="rgba(255,255,255,0.08)"
              strokeWidth={8}
            />
            <circle
              cx={130}
              cy={130}
              r={110}
              fill="none"
              stroke={mode === "focus" ? "#7c3aed" : "#10b981"}
              strokeWidth={8}
              strokeDasharray={circumference}
              strokeDashoffset={dash}
              strokeLinecap="round"
              style={{ transition: "stroke-dashoffset 0.8s ease" }}
            />
          </svg>
          <div className="absolute flex flex-col items-center">
            <span className="text-6xl font-mono font-bold text-white tracking-tight">
              {formatTime(timeLeft)}
            </span>
            <span className="text-white/50 text-sm mt-1 capitalize">
              {mode} mode
            </span>
          </div>
        </div>

        {/* Timer controls */}
        <div className="flex items-center gap-3">
          <button
            onClick={() => setTimerRunning((r) => !r)}
            disabled={!session}
            title={session ? undefined : "Start a session first"}
            className="flex items-center gap-2 px-6 py-2.5 bg-violet-600 hover:bg-violet-500 disabled:opacity-40 text-white rounded-full font-semibold transition"
          >
            {timerRunning ? (
              <Pause className="size-4" />
            ) : (
              <Play className="size-4" />
            )}
            {timerRunning ? "Pause" : "Resume"}
          </button>
          <button
            onClick={() => {
              setTimerRunning(false);
              setTimeLeft(mode === "focus" ? FOCUS_SECS : BREAK_SECS);
            }}
            className="px-4 py-2.5 bg-white/10 hover:bg-white/20 text-white rounded-full transition text-sm"
          >
            Reset
          </button>
        </div>

        {/* Session CTA */}
        {!session ? (
          <button
            onClick={startSession}
            className="mt-2 px-8 py-3 bg-emerald-600 hover:bg-emerald-500 text-white font-bold rounded-full shadow-lg transition text-lg"
          >
            🚀 Start Session
          </button>
        ) : (
          <div className="flex flex-col items-center gap-2 mt-2">
            <p className="text-white/60 text-sm">
              Session #{session.id} active — timer running
            </p>
            <button
              onClick={endSession}
              className="flex items-center gap-2 px-6 py-2.5 bg-red-600 hover:bg-red-500 text-white rounded-full font-semibold transition"
            >
              <Square className="size-4" />
              End Session & Earn Points
            </button>
          </div>
        )}
      </div>

      {/* ── RIGHT PANEL TOGGLE: Backgrounds ───────────────────────────────── */}
      <div className="fixed right-0 top-1/2 -translate-y-1/2 z-20 flex items-stretch">
        {/* Panel */}
        <div
          className={clsx(
            "bg-black/70 backdrop-blur-xl border-l border-white/10 transition-all duration-300 overflow-hidden",
            showBgPanel ? "w-64" : "w-0",
          )}
        >
          <div className="w-64 p-4 h-full overflow-y-auto">
            <h3 className="text-white font-semibold mb-3 flex items-center gap-2">
              <Image className="size-4 text-violet-400" /> Backgrounds
            </h3>
            {savedBgs.length === 0 ? (
              <p className="text-white/40 text-sm">
                No saved backgrounds. Visit Shop!
              </p>
            ) : (
              <div className="grid grid-cols-2 gap-2">
                {savedBgs.map((bg, i) => (
                  <button
                    key={bg.id}
                    onClick={() => setBgIdx(i)}
                    className={clsx(
                      "relative aspect-video rounded-lg overflow-hidden border-2 transition",
                      i === bgIdx
                        ? "border-violet-500"
                        : "border-transparent hover:border-white/30",
                    )}
                  >
                    <img
                      src={bg.imageUrl}
                      alt={bg.name}
                      className="w-full h-full object-cover"
                      onError={(e) => {
                        (e.target as HTMLImageElement).style.display = "none";
                      }}
                    />
                    <div className="absolute inset-0 bg-black/30 flex items-end p-1">
                      <span className="text-white text-xs truncate">
                        {bg.name}
                      </span>
                    </div>
                  </button>
                ))}
              </div>
            )}
          </div>
        </div>
        {/* Toggle button */}
        <button
          onClick={() => setShowBgPanel((s) => !s)}
          className="flex items-center justify-center w-8 bg-black/60 backdrop-blur-xl border-l border-y border-white/10 rounded-l-lg text-white/70 hover:text-white transition"
        >
          {showBgPanel ? (
            <ChevronRight className="size-4" />
          ) : (
            <ChevronLeft className="size-4" />
          )}
        </button>
      </div>

      {/* ── LEFT PANEL TOGGLE: Music ───────────────────────────────────────── */}
      <div className="fixed left-0 top-1/2 -translate-y-1/2 z-20 flex items-stretch">
        {/* Toggle button */}
        <button
          onClick={() => setShowMusicPanel((s) => !s)}
          className="flex items-center justify-center w-8 bg-black/60 backdrop-blur-xl border-r border-y border-white/10 rounded-r-lg text-white/70 hover:text-white transition"
        >
          {showMusicPanel ? (
            <ChevronLeft className="size-4" />
          ) : (
            <ChevronRight className="size-4" />
          )}
        </button>
        {/* Panel */}
        <div
          className={clsx(
            "bg-black/70 backdrop-blur-xl border-r border-white/10 transition-all duration-300 overflow-hidden",
            showMusicPanel ? "w-64" : "w-0",
          )}
        >
          <div className="w-64 p-4 h-full overflow-y-auto">
            <h3 className="text-white font-semibold mb-3 flex items-center gap-2">
              <Music className="size-4 text-violet-400" /> My Playlist
            </h3>
            {savedMusic.length === 0 ? (
              <p className="text-white/40 text-sm">
                No saved music. Visit Shop!
              </p>
            ) : (
              <div className="flex flex-col gap-1">
                {savedMusic.map((m, i) => (
                  <button
                    key={m.id}
                    onClick={() => {
                      setMusicIdx(i);
                      setMusicPlaying(true);
                      const audio = audioRef.current;
                      if (audio) {
                        audio.src = m.musicUrl;
                        audio.play().catch(() => {});
                      }
                    }}
                    className={clsx(
                      "flex items-center gap-2 px-3 py-2 rounded-lg text-sm text-left transition",
                      i === musicIdx
                        ? "bg-violet-600/40 text-white"
                        : "text-white/70 hover:bg-white/10 hover:text-white",
                    )}
                  >
                    <Music className="size-3 shrink-0" />
                    <span className="truncate">{m.title}</span>
                    <span className="ml-auto text-white/40 text-xs">
                      {Math.round(m.duration / 60)}m
                    </span>
                  </button>
                ))}
              </div>
            )}
          </div>
        </div>
      </div>

      {/* ── BOTTOM MUSIC BAR ─────────────────────────────────────────────── */}
      <div className="fixed bottom-0 inset-x-0 z-20 flex items-center justify-between px-6 py-3 bg-black/60 backdrop-blur-xl border-t border-white/10">
        {/* Track info */}
        <div className="flex items-center gap-3 w-48">
          <div className="size-9 rounded-lg bg-violet-700/50 flex items-center justify-center shrink-0">
            <Music className="size-4 text-violet-300" />
          </div>
          <div className="overflow-hidden">
            <p className="text-white text-sm font-medium truncate">
              {savedMusic[musicIdx]?.title ?? "No track"}
            </p>
            <p className="text-white/40 text-xs">Ambient</p>
          </div>
        </div>

        {/* Controls */}
        <div className="flex items-center gap-3">
          <button
            onClick={prevTrack}
            className="text-white/60 hover:text-white transition"
          >
            <SkipBack className="size-5" />
          </button>
          <button
            onClick={toggleMusicPlay}
            className="size-10 rounded-full bg-violet-600 hover:bg-violet-500 flex items-center justify-center text-white transition"
          >
            {musicPlaying ? (
              <Pause className="size-5" />
            ) : (
              <Play className="size-5" />
            )}
          </button>
          <button
            onClick={nextTrack}
            className="text-white/60 hover:text-white transition"
          >
            <SkipForward className="size-5" />
          </button>
        </div>

        {/* Volume */}
        <div className="flex items-center gap-2 w-48 justify-end">
          <button
            onClick={() => setMuted((m) => !m)}
            className="text-white/60 hover:text-white transition"
          >
            {muted ? (
              <VolumeX className="size-4" />
            ) : (
              <Volume2 className="size-4" />
            )}
          </button>
          <input
            type="range"
            min={0}
            max={1}
            step={0.01}
            value={muted ? 0 : volume}
            onChange={(e) => {
              setVolume(Number(e.target.value));
              setMuted(false);
            }}
            className="w-24 accent-violet-500"
          />
          {/* Points badge */}
          {session && (
            <div className="ml-4 flex items-center gap-1 text-yellow-400 text-sm font-medium">
              <Star className="size-4 fill-yellow-400" />
              <span>Active</span>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
