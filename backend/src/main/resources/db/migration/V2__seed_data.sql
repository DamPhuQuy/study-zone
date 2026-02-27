-- =====================================================
-- =====================================================
-- Role values: USER=0, ADMIN=1, GUEST=2 (enum ordinal)
-- =====================================================
-- =====================================================

-- -------------------------
-- Users
-- -------------------------
INSERT INTO users (username, email, password, total_points, total_times, avatar_url, role, created_at, updated_at) VALUES
('admin',       'admin@studyzone.com',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 9999, 36000, 'https://api.dicebear.com/7.x/avataaars/svg?seed=admin',   1, CURRENT_TIMESTAMP - interval '30 days', CURRENT_TIMESTAMP),
('alice',       'alice@example.com',     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',  350,  7200, 'https://api.dicebear.com/7.x/avataaars/svg?seed=alice',   0, CURRENT_TIMESTAMP - interval '25 days', CURRENT_TIMESTAMP),
('bob',         'bob@example.com',       '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',  210,  5400, 'https://api.dicebear.com/7.x/avataaars/svg?seed=bob',     0, CURRENT_TIMESTAMP - interval '20 days', CURRENT_TIMESTAMP),
('charlie',     'charlie@example.com',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',  540,  9000, 'https://api.dicebear.com/7.x/avataaars/svg?seed=charlie', 0, CURRENT_TIMESTAMP - interval '18 days', CURRENT_TIMESTAMP),
('diana',       'diana@example.com',     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',   80,  1800, 'https://api.dicebear.com/7.x/avataaars/svg?seed=diana',   0, CURRENT_TIMESTAMP - interval '10 days', CURRENT_TIMESTAMP),
('evan',        'evan@example.com',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',    0,     0, NULL,                                                      2, CURRENT_TIMESTAMP - interval '2 days',  CURRENT_TIMESTAMP);

-- -------------------------
-- Music
-- -------------------------
INSERT INTO music (title, music_url, duration, required_point, is_active, created_at) VALUES
('Lo-Fi Chill Beats',      'https://cdn.studyzone.com/music/lofi-chill.mp3',        3600, 0,   TRUE,  CURRENT_TIMESTAMP - interval '30 days'),
('Deep Focus Flow',        'https://cdn.studyzone.com/music/deep-focus.mp3',         2700, 0,   TRUE,  CURRENT_TIMESTAMP - interval '29 days'),
('Rain & Thunder Ambience','https://cdn.studyzone.com/music/rain-thunder.mp3',       3900, 50,  TRUE,  CURRENT_TIMESTAMP - interval '28 days'),
('Jazz Coffee Shop',       'https://cdn.studyzone.com/music/jazz-coffee.mp3',        3300, 50,  TRUE,  CURRENT_TIMESTAMP - interval '27 days'),
('Epic Orchestra Study',   'https://cdn.studyzone.com/music/epic-orchestra.mp3',     4200, 100, TRUE,  CURRENT_TIMESTAMP - interval '26 days'),
('Nature Forest Sounds',   'https://cdn.studyzone.com/music/nature-forest.mp3',      5400, 100, TRUE,  CURRENT_TIMESTAMP - interval '25 days'),
('Piano Meditation',       'https://cdn.studyzone.com/music/piano-meditation.mp3',   3000, 200, TRUE,  CURRENT_TIMESTAMP - interval '20 days'),
('Synthwave Retro Study',  'https://cdn.studyzone.com/music/synthwave-retro.mp3',    3600, 200, FALSE, CURRENT_TIMESTAMP - interval '15 days');

-- -------------------------
-- Backgrounds
-- -------------------------
INSERT INTO backgrounds (name, image_url, required_point, is_active, created_at) VALUES
('Default Library',    'https://cdn.studyzone.com/bg/library.jpg',       0,   TRUE,  CURRENT_TIMESTAMP - interval '30 days'),
('Cozy Cafe',          'https://cdn.studyzone.com/bg/cafe.jpg',           0,   TRUE,  CURRENT_TIMESTAMP - interval '29 days'),
('Rainy Window',       'https://cdn.studyzone.com/bg/rainy-window.jpg',   50,  TRUE,  CURRENT_TIMESTAMP - interval '28 days'),
('Mountain Sunrise',   'https://cdn.studyzone.com/bg/mountain.jpg',       50,  TRUE,  CURRENT_TIMESTAMP - interval '27 days'),
('Space Station',      'https://cdn.studyzone.com/bg/space.jpg',          100, TRUE,  CURRENT_TIMESTAMP - interval '26 days'),
('Japanese Garden',    'https://cdn.studyzone.com/bg/japanese-garden.jpg',100, TRUE,  CURRENT_TIMESTAMP - interval '25 days'),
('Neon City Night',    'https://cdn.studyzone.com/bg/neon-city.jpg',      200, TRUE,  CURRENT_TIMESTAMP - interval '20 days'),
('Underwater World',   'https://cdn.studyzone.com/bg/underwater.jpg',     200, FALSE, CURRENT_TIMESTAMP - interval '15 days');

-- -------------------------
-- Study sessions
-- -------------------------
INSERT INTO study_session (user_id, start_time, end_time, points_earned) VALUES
-- alice
(2, CURRENT_TIMESTAMP - interval '24 days' + interval '9 hours',  CURRENT_TIMESTAMP - interval '24 days' + interval '11 hours', 20),
(2, CURRENT_TIMESTAMP - interval '22 days' + interval '14 hours', CURRENT_TIMESTAMP - interval '22 days' + interval '16 hours', 20),
(2, CURRENT_TIMESTAMP - interval '20 days' + interval '10 hours', CURRENT_TIMESTAMP - interval '20 days' + interval '13 hours', 30),
-- bob
(3, CURRENT_TIMESTAMP - interval '19 days' + interval '8 hours',  CURRENT_TIMESTAMP - interval '19 days' + interval '10 hours', 20),
(3, CURRENT_TIMESTAMP - interval '17 days' + interval '20 hours', CURRENT_TIMESTAMP - interval '17 days' + interval '22 hours', 20),
-- charlie
(4, CURRENT_TIMESTAMP - interval '17 days' + interval '9 hours',  CURRENT_TIMESTAMP - interval '17 days' + interval '12 hours', 30),
(4, CURRENT_TIMESTAMP - interval '15 days' + interval '13 hours', CURRENT_TIMESTAMP - interval '15 days' + interval '16 hours', 30),
(4, CURRENT_TIMESTAMP - interval '12 days' + interval '10 hours', CURRENT_TIMESTAMP - interval '12 days' + interval '14 hours', 40),
(4, CURRENT_TIMESTAMP - interval '10 days' + interval '9 hours',  CURRENT_TIMESTAMP - interval '10 days' + interval '13 hours', 40),
-- diana
(5, CURRENT_TIMESTAMP - interval '8 days' + interval '11 hours', CURRENT_TIMESTAMP - interval '8 days' + interval '12 hours', 10);

-- -------------------------
-- Music unlocked by users
-- (only unlock music with required_point > 0)
-- -------------------------
INSERT INTO music_unlocked (user_id, music_id, points_used, unlocked_at) VALUES
-- alice unlocked Rain & Thunder (50), Jazz Coffee (50)
(2, 3, 50, CURRENT_TIMESTAMP - interval '20 days'),
(2, 4, 50, CURRENT_TIMESTAMP - interval '18 days'),
-- bob unlocked Rain & Thunder (50)
(3, 3, 50, CURRENT_TIMESTAMP - interval '15 days'),
-- charlie unlocked Rain & Thunder (50), Jazz Coffee (50), Epic Orchestra (100), Nature Forest (100)
(4, 3, 50,  CURRENT_TIMESTAMP - interval '16 days'),
(4, 4, 50,  CURRENT_TIMESTAMP - interval '14 days'),
(4, 5, 100, CURRENT_TIMESTAMP - interval '12 days'),
(4, 6, 100, CURRENT_TIMESTAMP - interval '10 days');

-- -------------------------
-- Music saved by users
-- -------------------------
INSERT INTO music_saved (user_id, music_id, saved_at) VALUES
-- alice saved Lo-Fi Chill, Deep Focus, Rain & Thunder
(2, 1, CURRENT_TIMESTAMP - interval '23 days'),
(2, 2, CURRENT_TIMESTAMP - interval '21 days'),
(2, 3, CURRENT_TIMESTAMP - interval '19 days'),
-- bob saved Lo-Fi Chill, Rain & Thunder
(3, 1, CURRENT_TIMESTAMP - interval '18 days'),
(3, 3, CURRENT_TIMESTAMP - interval '14 days'),
-- charlie saved all free + unlocked tracks
(4, 1, CURRENT_TIMESTAMP - interval '17 days'),
(4, 2, CURRENT_TIMESTAMP - interval '16 days'),
(4, 3, CURRENT_TIMESTAMP - interval '15 days'),
(4, 4, CURRENT_TIMESTAMP - interval '13 days'),
(4, 5, CURRENT_TIMESTAMP - interval '11 days'),
(4, 6, CURRENT_TIMESTAMP - interval '9 days');

-- -------------------------
-- Backgrounds unlocked by users
-- -------------------------
INSERT INTO background_unlocked (user_id, background_id, points_used, unlocked_at) VALUES
-- alice unlocked Rainy Window (50), Mountain Sunrise (50)
(2, 3, 50, CURRENT_TIMESTAMP - interval '19 days'),
(2, 4, 50, CURRENT_TIMESTAMP - interval '17 days'),
-- bob unlocked Rainy Window (50)
(3, 3, 50, CURRENT_TIMESTAMP - interval '14 days'),
-- charlie unlocked Rainy Window, Mountain Sunrise, Space Station, Japanese Garden
(4, 3, 50,  CURRENT_TIMESTAMP - interval '15 days'),
(4, 4, 50,  CURRENT_TIMESTAMP - interval '13 days'),
(4, 5, 100, CURRENT_TIMESTAMP - interval '11 days'),
(4, 6, 100, CURRENT_TIMESTAMP - interval '9 days');

-- -------------------------
-- Backgrounds saved by users
-- -------------------------
INSERT INTO background_saved (user_id, background_id, saved_at) VALUES
-- alice saved Default Library, Cozy Cafe, Rainy Window
(2, 1, CURRENT_TIMESTAMP - interval '23 days'),
(2, 2, CURRENT_TIMESTAMP - interval '22 days'),
(2, 3, CURRENT_TIMESTAMP - interval '18 days'),
-- bob saved Default Library, Rainy Window
(3, 1, CURRENT_TIMESTAMP - interval '17 days'),
(3, 3, CURRENT_TIMESTAMP - interval '13 days'),
-- charlie saved all free + unlocked backgrounds
(4, 1, CURRENT_TIMESTAMP - interval '16 days'),
(4, 2, CURRENT_TIMESTAMP - interval '15 days'),
(4, 3, CURRENT_TIMESTAMP - interval '14 days'),
(4, 4, CURRENT_TIMESTAMP - interval '12 days'),
(4, 5, CURRENT_TIMESTAMP - interval '10 days'),
(4, 6, CURRENT_TIMESTAMP - interval '8 days');
