-- =====================================================
-- V2__seed_data.sql
-- Seed data for Study Zone application
-- All passwords are bcrypt of "password123"
-- =====================================================

-- -------------------------
-- Users
-- -------------------------
INSERT INTO users (username, email, password, total_points, total_times, avatar_url, role, created_at, updated_at) VALUES
('admin',       'admin@studyzone.com',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 9999, 36000, 'https://api.dicebear.com/7.x/avataaars/svg?seed=admin',   'ADMIN', DATEADD(day, -30, GETDATE()), GETDATE()),
('alice',       'alice@example.com',     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',  350,  7200, 'https://api.dicebear.com/7.x/avataaars/svg?seed=alice',   'USER',  DATEADD(day, -25, GETDATE()), GETDATE()),
('bob',         'bob@example.com',       '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',  210,  5400, 'https://api.dicebear.com/7.x/avataaars/svg?seed=bob',     'USER',  DATEADD(day, -20, GETDATE()), GETDATE()),
('charlie',     'charlie@example.com',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',  540,  9000, 'https://api.dicebear.com/7.x/avataaars/svg?seed=charlie', 'USER',  DATEADD(day, -18, GETDATE()), GETDATE()),
('diana',       'diana@example.com',     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',   80,  1800, 'https://api.dicebear.com/7.x/avataaars/svg?seed=diana',   'USER',  DATEADD(day, -10, GETDATE()), GETDATE()),
('evan',        'evan@example.com',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',    0,     0, NULL,                                                      'GUEST', DATEADD(day, -2, GETDATE()),  GETDATE());

-- -------------------------
-- Music
-- -------------------------
INSERT INTO music (title, music_url, duration, required_point, is_active, created_at) VALUES
('Lo-Fi Chill Beats',      'https://cdn.studyzone.com/music/lofi-chill.mp3',        3600, 0,   TRUE,  DATEADD(day, -30, GETDATE())),
('Deep Focus Flow',        'https://cdn.studyzone.com/music/deep-focus.mp3',         2700, 0,   TRUE,  DATEADD(day, -29, GETDATE())),
('Rain & Thunder Ambience','https://cdn.studyzone.com/music/rain-thunder.mp3',       3900, 50,  TRUE,  DATEADD(day, -28, GETDATE())),
('Jazz Coffee Shop',       'https://cdn.studyzone.com/music/jazz-coffee.mp3',        3300, 50,  TRUE,  DATEADD(day, -27, GETDATE())),
('Epic Orchestra Study',   'https://cdn.studyzone.com/music/epic-orchestra.mp3',     4200, 100, TRUE,  DATEADD(day, -26, GETDATE())),
('Nature Forest Sounds',   'https://cdn.studyzone.com/music/nature-forest.mp3',      5400, 100, TRUE,  DATEADD(day, -25, GETDATE())),
('Piano Meditation',       'https://cdn.studyzone.com/music/piano-meditation.mp3',   3000, 200, TRUE,  DATEADD(day, -20, GETDATE())),
('Synthwave Retro Study',  'https://cdn.studyzone.com/music/synthwave-retro.mp3',    3600, 200, FALSE, DATEADD(day, -15, GETDATE()));

-- -------------------------
-- Backgrounds
-- -------------------------
INSERT INTO backgrounds (name, image_url, required_point, is_active, created_at) VALUES
('Default Library',    'https://cdn.studyzone.com/bg/library.jpg',       0,   TRUE,  DATEADD(day, -30, GETDATE())),
('Cozy Cafe',          'https://cdn.studyzone.com/bg/cafe.jpg',           0,   TRUE,  DATEADD(day, -29, GETDATE())),
('Rainy Window',       'https://cdn.studyzone.com/bg/rainy-window.jpg',   50,  TRUE,  DATEADD(day, -28, GETDATE())),
('Mountain Sunrise',   'https://cdn.studyzone.com/bg/mountain.jpg',       50,  TRUE,  DATEADD(day, -27, GETDATE())),
('Space Station',      'https://cdn.studyzone.com/bg/space.jpg',          100, TRUE,  DATEADD(day, -26, GETDATE())),
('Japanese Garden',    'https://cdn.studyzone.com/bg/japanese-garden.jpg',100, TRUE,  DATEADD(day, -25, GETDATE())),
('Neon City Night',    'https://cdn.studyzone.com/bg/neon-city.jpg',      200, TRUE,  DATEADD(day, -20, GETDATE())),
('Underwater World',   'https://cdn.studyzone.com/bg/underwater.jpg',     200, FALSE, DATEADD(day, -15, GETDATE()));

-- -------------------------
-- Study sessions
-- -------------------------
INSERT INTO study_session (user_id, start_time, end_time, points_earned) VALUES
-- alice
(2, DATEADD(hour, 9, DATEADD(day, -24, GETDATE())),  DATEADD(hour, 11, DATEADD(day, -24, GETDATE())), 20),
(2, DATEADD(hour, 14, DATEADD(day, -22, GETDATE())), DATEADD(hour, 16, DATEADD(day, -22, GETDATE())), 20),
(2, DATEADD(hour, 10, DATEADD(day, -20, GETDATE())), DATEADD(hour, 13, DATEADD(day, -20, GETDATE())), 30),
-- bob
(3, DATEADD(hour, 8, DATEADD(day, -19, GETDATE())),  DATEADD(hour, 10, DATEADD(day, -19, GETDATE())), 20),
(3, DATEADD(hour, 20, DATEADD(day, -17, GETDATE())), DATEADD(hour, 22, DATEADD(day, -17, GETDATE())), 20),
-- charlie
(4, DATEADD(hour, 9, DATEADD(day, -17, GETDATE())),  DATEADD(hour, 12, DATEADD(day, -17, GETDATE())), 30),
(4, DATEADD(hour, 13, DATEADD(day, -15, GETDATE())), DATEADD(hour, 16, DATEADD(day, -15, GETDATE())), 30),
(4, DATEADD(hour, 10, DATEADD(day, -12, GETDATE())), DATEADD(hour, 14, DATEADD(day, -12, GETDATE())), 40),
(4, DATEADD(hour, 9, DATEADD(day, -10, GETDATE())),  DATEADD(hour, 13, DATEADD(day, -10, GETDATE())), 40),
-- diana
(5, DATEADD(hour, 11, DATEADD(day, -8, GETDATE())), DATEADD(hour, 12, DATEADD(day, -8, GETDATE())), 10);

-- -------------------------
-- Music unlocked by users
-- (only unlock music with required_point > 0)
-- -------------------------
INSERT INTO music_unlocked (user_id, music_id, points_used, unlocked_at) VALUES
-- alice unlocked Rain & Thunder (50), Jazz Coffee (50)
(2, 3, 50, DATEADD(day, -20, GETDATE())),
(2, 4, 50, DATEADD(day, -18, GETDATE())),
-- bob unlocked Rain & Thunder (50)
(3, 3, 50, DATEADD(day, -15, GETDATE())),
-- charlie unlocked Rain & Thunder (50), Jazz Coffee (50), Epic Orchestra (100), Nature Forest (100)
(4, 3, 50,  DATEADD(day, -16, GETDATE())),
(4, 4, 50,  DATEADD(day, -14, GETDATE())),
(4, 5, 100, DATEADD(day, -12, GETDATE())),
(4, 6, 100, DATEADD(day, -10, GETDATE()));

-- -------------------------
-- Music saved by users
-- -------------------------
INSERT INTO music_saved (user_id, music_id, saved_at) VALUES
-- alice saved Lo-Fi Chill, Deep Focus, Rain & Thunder
(2, 1, DATEADD(day, -23, GETDATE())),
(2, 2, DATEADD(day, -21, GETDATE())),
(2, 3, DATEADD(day, -19, GETDATE())),
-- bob saved Lo-Fi Chill, Rain & Thunder
(3, 1, DATEADD(day, -18, GETDATE())),
(3, 3, DATEADD(day, -14, GETDATE())),
-- charlie saved all free + unlocked tracks
(4, 1, DATEADD(day, -17, GETDATE())),
(4, 2, DATEADD(day, -16, GETDATE())),
(4, 3, DATEADD(day, -15, GETDATE())),
(4, 4, DATEADD(day, -13, GETDATE())),
(4, 5, DATEADD(day, -11, GETDATE())),
(4, 6, DATEADD(day, -9, GETDATE()));

-- -------------------------
-- Backgrounds unlocked by users
-- -------------------------
INSERT INTO background_unlocked (user_id, background_id, points_used, unlocked_at) VALUES
-- alice unlocked Rainy Window (50), Mountain Sunrise (50)
(2, 3, 50, DATEADD(day, -19, GETDATE())),
(2, 4, 50, DATEADD(day, -17, GETDATE())),
-- bob unlocked Rainy Window (50)
(3, 3, 50, DATEADD(day, -14, GETDATE())),
-- charlie unlocked Rainy Window, Mountain Sunrise, Space Station, Japanese Garden
(4, 3, 50,  DATEADD(day, -15, GETDATE())),
(4, 4, 50,  DATEADD(day, -13, GETDATE())),
(4, 5, 100, DATEADD(day, -11, GETDATE())),
(4, 6, 100, DATEADD(day, -9, GETDATE()));

-- -------------------------
-- Backgrounds saved by users
-- -------------------------
INSERT INTO background_saved (user_id, background_id, saved_at) VALUES
-- alice saved Default Library, Cozy Cafe, Rainy Window
(2, 1, DATEADD(day, -23, GETDATE())),
(2, 2, DATEADD(day, -22, GETDATE())),
(2, 3, DATEADD(day, -18, GETDATE())),
-- bob saved Default Library, Rainy Window
(3, 1, DATEADD(day, -17, GETDATE())),
(3, 3, DATEADD(day, -13, GETDATE())),
-- charlie saved all free + unlocked backgrounds
(4, 1, DATEADD(day, -16, GETDATE())),
(4, 2, DATEADD(day, -15, GETDATE())),
(4, 3, DATEADD(day, -14, GETDATE())),
(4, 4, DATEADD(day, -12, GETDATE())),
(4, 5, DATEADD(day, -10, GETDATE())),
(4, 6, DATEADD(day, -8, GETDATE()));
