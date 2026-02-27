import { useEffect, useState } from 'react';
import { musicApi } from '../api/music';
import { backgroundApi } from '../api/background';
import { userApi } from '../api/user';
import type {
  MusicSavedResponseDTO,
  MusicUnlockedResponseDTO,
  BackgroundSavedResponseDTO,
  BackgroundUnlockedResponseDTO,
} from '../types';
import toast from 'react-hot-toast';
import { Music, Image, Lock, Star, Check, Heart } from 'lucide-react';
import clsx from 'clsx';

// ─── Mock "all available" items ───────────────────────────────────────────────
// In production these would come from a /api/v1/music and /api/v1/backgrounds list endpoint.
// We model them from the seed data so the UI is complete.
const ALL_MUSIC = [
  { id: 1, title: 'Lo-Fi Chill Beats', duration: 3600, requiredPoint: 0 },
  { id: 2, title: 'Deep Focus Flow', duration: 2700, requiredPoint: 0 },
  { id: 3, title: 'Rain & Thunder Ambience', duration: 3900, requiredPoint: 50 },
  { id: 4, title: 'Jazz Coffee Shop', duration: 3300, requiredPoint: 50 },
  { id: 5, title: 'Epic Orchestra Study', duration: 4200, requiredPoint: 100 },
  { id: 6, title: 'Nature Forest Sounds', duration: 5400, requiredPoint: 100 },
  { id: 7, title: 'Piano Meditation', duration: 3000, requiredPoint: 200 },
];

const ALL_BACKGROUNDS = [
  { id: 1, name: 'Default Library', imageUrl: 'https://images.unsplash.com/photo-1507842217343-583bb7270b66?w=400', requiredPoint: 0 },
  { id: 2, name: 'Cozy Cafe', imageUrl: 'https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=400', requiredPoint: 0 },
  { id: 3, name: 'Rainy Window', imageUrl: 'https://images.unsplash.com/photo-1428592953211-077101b2021b?w=400', requiredPoint: 50 },
  { id: 4, name: 'Mountain Sunrise', imageUrl: 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400', requiredPoint: 50 },
  { id: 5, name: 'Space Station', imageUrl: 'https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?w=400', requiredPoint: 100 },
  { id: 6, name: 'Japanese Garden', imageUrl: 'https://images.unsplash.com/photo-1528360983277-13d401cdc186?w=400', requiredPoint: 100 },
  { id: 7, name: 'Neon City Night', imageUrl: 'https://images.unsplash.com/photo-1477959858617-67f85cf4f1df?w=400', requiredPoint: 200 },
];

type Tab = 'music' | 'backgrounds';

export default function ShopPage() {
  const [tab, setTab] = useState<Tab>('music');
  const [points, setPoints] = useState(0);
  const [savedMusic, setSavedMusic] = useState<MusicSavedResponseDTO[]>([]);
  const [unlockedMusic, setUnlockedMusic] = useState<MusicUnlockedResponseDTO[]>([]);
  const [savedBgs, setSavedBgs] = useState<BackgroundSavedResponseDTO[]>([]);
  const [unlockedBgs, setUnlockedBgs] = useState<BackgroundUnlockedResponseDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [busy, setBusy] = useState<number | null>(null); // id of item being processed

  useEffect(() => {
    setLoading(true);
    Promise.all([
      userApi.getPoints(),
      musicApi.getSaved(),
      musicApi.getUnlocked(),
      backgroundApi.getSaved(),
      backgroundApi.getUnlocked(),
    ])
      .then(([pts, sm, um, sb, ub]) => {
        setPoints(pts.data.totalPoints);
        setSavedMusic(sm.data);
        setUnlockedMusic(um.data);
        setSavedBgs(sb.data);
        setUnlockedBgs(ub.data);
      })
      .catch(() => toast.error('Failed to load shop data'))
      .finally(() => setLoading(false));
  }, []);

  // ─── Music helpers ────────────────────────────────────────────────────────
  const isMusicUnlocked = (id: number) =>
    id <= 2 || unlockedMusic.some((u) => u.musicId === id); // free tracks always "unlocked"
  const isMusicSaved = (id: number) => savedMusic.some((s) => s.musicId === id);

  const handleUnlockMusic = async (id: number, cost: number) => {
    if (points < cost) { toast.error('Not enough points!'); return; }
    setBusy(id);
    try {
      await musicApi.unlock(id);
      setPoints((p) => p - cost);
      const res = await musicApi.getUnlocked();
      setUnlockedMusic(res.data);
      toast.success('Music unlocked! 🎵');
    } catch {
      toast.error('Failed to unlock music');
    } finally {
      setBusy(null);
    }
  };

  const handleSaveMusic = async (id: number) => {
    setBusy(id);
    try {
      await musicApi.save(id);
      const res = await musicApi.getSaved();
      setSavedMusic(res.data);
      toast.success('Added to playlist!');
    } catch {
      toast.error('Failed to save music');
    } finally {
      setBusy(null);
    }
  };

  const handleRemoveSavedMusic = async (musicId: number) => {
    const saved = savedMusic.find((s) => s.musicId === musicId);
    if (!saved) return;
    setBusy(musicId);
    try {
      await musicApi.removeSaved(musicId);
      setSavedMusic((prev) => prev.filter((s) => s.musicId !== musicId));
      toast.success('Removed from playlist');
    } catch {
      toast.error('Failed to remove');
    } finally {
      setBusy(null);
    }
  };

  // ─── Background helpers ───────────────────────────────────────────────────
  const isBgUnlocked = (id: number) =>
    id <= 2 || unlockedBgs.some((u) => u.backgroundId === id);
  const isBgSaved = (id: number) => savedBgs.some((s) => s.backgroundId === id);

  const handleUnlockBg = async (id: number, cost: number) => {
    if (points < cost) { toast.error('Not enough points!'); return; }
    setBusy(id);
    try {
      await backgroundApi.unlock(id);
      setPoints((p) => p - cost);
      const res = await backgroundApi.getUnlocked();
      setUnlockedBgs(res.data);
      toast.success('Background unlocked! 🖼️');
    } catch {
      toast.error('Failed to unlock background');
    } finally {
      setBusy(null);
    }
  };

  const handleSaveBg = async (id: number) => {
    setBusy(id);
    try {
      await backgroundApi.save(id);
      const res = await backgroundApi.getSaved();
      setSavedBgs(res.data);
      toast.success('Background saved!');
    } catch {
      toast.error('Failed to save background');
    } finally {
      setBusy(null);
    }
  };

  const handleRemoveSavedBg = async (bgId: number) => {
    setBusy(bgId);
    try {
      await backgroundApi.removeSaved(bgId);
      setSavedBgs((prev) => prev.filter((s) => s.backgroundId !== bgId));
      toast.success('Removed from saved');
    } catch {
      toast.error('Failed to remove');
    } finally {
      setBusy(null);
    }
  };

  return (
    <div className="max-w-5xl mx-auto px-4 py-8">
      {/* Header */}
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 mb-8">
        <div>
          <h1 className="text-2xl font-bold text-white">Shop</h1>
          <p className="text-white/50 text-sm mt-1">
            Unlock music and backgrounds with your study points.
          </p>
        </div>
        <div className="flex items-center gap-2 bg-yellow-500/10 border border-yellow-500/30 rounded-xl px-4 py-2">
          <Star className="size-4 text-yellow-400 fill-yellow-400" />
          <span className="text-yellow-300 font-semibold">{points.toFixed(0)} pts</span>
        </div>
      </div>

      {/* Tabs */}
      <div className="flex gap-2 mb-6">
        {([
          { key: 'music', label: 'Music', icon: Music },
          { key: 'backgrounds', label: 'Backgrounds', icon: Image },
        ] as { key: Tab; label: string; icon: typeof Music }[]).map(({ key, label, icon: Icon }) => (
          <button
            key={key}
            onClick={() => setTab(key)}
            className={clsx(
              'flex items-center gap-2 px-5 py-2 rounded-xl font-medium text-sm transition',
              tab === key
                ? 'bg-violet-600 text-white'
                : 'bg-white/5 text-white/60 hover:bg-white/10 hover:text-white'
            )}
          >
            <Icon className="size-4" />
            {label}
          </button>
        ))}
      </div>

      {loading ? (
        <div className="flex items-center justify-center py-24">
          <div className="animate-spin size-8 border-4 border-violet-500 border-t-transparent rounded-full" />
        </div>
      ) : tab === 'music' ? (
        // ── Music grid ─────────────────────────────────────────────────────
        <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
          {ALL_MUSIC.map((item) => {
            const unlocked = isMusicUnlocked(item.id);
            const saved = isMusicSaved(item.id);
            const isBusy = busy === item.id;
            const canAfford = points >= item.requiredPoint;

            return (
              <div
                key={item.id}
                className="bg-white/5 border border-white/10 rounded-xl p-4 flex flex-col gap-3 hover:border-violet-500/40 transition"
              >
                {/* Icon */}
                <div className="size-12 rounded-xl bg-violet-700/30 flex items-center justify-center">
                  <Music className="size-6 text-violet-300" />
                </div>

                {/* Info */}
                <div>
                  <h3 className="text-white font-semibold text-sm">{item.title}</h3>
                  <p className="text-white/40 text-xs mt-0.5">
                    {Math.round(item.duration / 60)} min
                  </p>
                </div>

                {/* Cost badge */}
                <div className="flex items-center gap-1">
                  {item.requiredPoint === 0 ? (
                    <span className="text-xs text-emerald-400 font-medium">Free</span>
                  ) : (
                    <span
                      className={clsx(
                        'flex items-center gap-1 text-xs font-medium',
                        unlocked
                          ? 'text-white/40'
                          : canAfford
                          ? 'text-yellow-400'
                          : 'text-red-400'
                      )}
                    >
                      <Star className="size-3 fill-current" />
                      {item.requiredPoint} pts
                    </span>
                  )}
                </div>

                {/* Actions */}
                <div className="flex gap-2 mt-auto">
                  {!unlocked ? (
                    <button
                      onClick={() => handleUnlockMusic(item.id, item.requiredPoint)}
                      disabled={isBusy || !canAfford}
                      className="flex-1 flex items-center justify-center gap-1.5 py-2 bg-violet-600 hover:bg-violet-500 disabled:opacity-40 text-white text-xs font-semibold rounded-lg transition"
                    >
                      <Lock className="size-3" />
                      {isBusy ? '…' : 'Unlock'}
                    </button>
                  ) : (
                    <button
                      onClick={saved ? () => handleRemoveSavedMusic(item.id) : () => handleSaveMusic(item.id)}
                      disabled={isBusy}
                      className={clsx(
                        'flex-1 flex items-center justify-center gap-1.5 py-2 text-xs font-semibold rounded-lg transition',
                        saved
                          ? 'bg-emerald-600/20 text-emerald-400 hover:bg-red-500/20 hover:text-red-400 border border-emerald-500/30'
                          : 'bg-white/10 hover:bg-white/20 text-white border border-white/10'
                      )}
                    >
                      {saved ? (
                        <>
                          <Check className="size-3" />
                          {isBusy ? '…' : 'Saved'}
                        </>
                      ) : (
                        <>
                          <Heart className="size-3" />
                          {isBusy ? '…' : 'Save'}
                        </>
                      )}
                    </button>
                  )}
                </div>
              </div>
            );
          })}
        </div>
      ) : (
        // ── Backgrounds grid ───────────────────────────────────────────────
        <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
          {ALL_BACKGROUNDS.map((item) => {
            const unlocked = isBgUnlocked(item.id);
            const saved = isBgSaved(item.id);
            const isBusy = busy === item.id;
            const canAfford = points >= item.requiredPoint;

            return (
              <div
                key={item.id}
                className="bg-white/5 border border-white/10 rounded-xl overflow-hidden hover:border-violet-500/40 transition"
              >
                {/* Preview */}
                <div className="relative aspect-video">
                  <img
                    src={item.imageUrl}
                    alt={item.name}
                    className={clsx('w-full h-full object-cover', !unlocked && 'blur-sm')}
                  />
                  {!unlocked && (
                    <div className="absolute inset-0 bg-black/50 flex items-center justify-center">
                      <Lock className="size-8 text-white/70" />
                    </div>
                  )}
                </div>

                {/* Details */}
                <div className="p-3 flex items-center justify-between gap-2">
                  <div>
                    <h3 className="text-white font-semibold text-sm">{item.name}</h3>
                    <div className="flex items-center gap-1 mt-0.5">
                      {item.requiredPoint === 0 ? (
                        <span className="text-xs text-emerald-400">Free</span>
                      ) : (
                        <span
                          className={clsx(
                            'flex items-center gap-1 text-xs',
                            unlocked ? 'text-white/40' : canAfford ? 'text-yellow-400' : 'text-red-400'
                          )}
                        >
                          <Star className="size-3 fill-current" />
                          {item.requiredPoint}
                        </span>
                      )}
                    </div>
                  </div>

                  <div className="flex gap-1.5">
                    {!unlocked ? (
                      <button
                        onClick={() => handleUnlockBg(item.id, item.requiredPoint)}
                        disabled={isBusy || !canAfford}
                        className="px-3 py-1.5 bg-violet-600 hover:bg-violet-500 disabled:opacity-40 text-white text-xs font-semibold rounded-lg transition"
                      >
                        {isBusy ? '…' : 'Unlock'}
                      </button>
                    ) : (
                      <button
                        onClick={saved ? () => handleRemoveSavedBg(item.id) : () => handleSaveBg(item.id)}
                        disabled={isBusy}
                        className={clsx(
                          'px-3 py-1.5 text-xs font-semibold rounded-lg transition',
                          saved
                            ? 'bg-emerald-600/20 text-emerald-400 hover:bg-red-500/20 hover:text-red-400'
                            : 'bg-white/10 hover:bg-white/20 text-white'
                        )}
                      >
                        {saved ? (isBusy ? '…' : 'Saved ✓') : isBusy ? '…' : 'Save'}
                      </button>
                    )}
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}
