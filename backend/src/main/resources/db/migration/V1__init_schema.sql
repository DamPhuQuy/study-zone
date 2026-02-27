-- =====================================================
-- V1__init_schema.sql
-- Initial schema for Study Zone application
-- =====================================================

-- Users table
CREATE TABLE users (
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(255) NOT NULL UNIQUE,
    email       VARCHAR(255),
    password    VARCHAR(255) NOT NULL,
    total_points DOUBLE PRECISION DEFAULT 0,
    total_times  BIGINT DEFAULT 0,
    avatar_url  VARCHAR(500),
    role        VARCHAR(50)  DEFAULT 'USER',
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP
);

-- Music table
CREATE TABLE music (
    id             BIGSERIAL PRIMARY KEY,
    title          VARCHAR(500) NOT NULL,
    music_url      VARCHAR(500) NOT NULL UNIQUE,
    duration       BIGINT       NOT NULL,
    required_point BIGINT       DEFAULT 0,
    is_active      BOOLEAN      DEFAULT TRUE,
    created_at     TIMESTAMP,
    updated_at     TIMESTAMP
);

-- Backgrounds table
CREATE TABLE backgrounds (
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(500) NOT NULL UNIQUE,
    image_url      VARCHAR(500),
    required_point BIGINT  DEFAULT 0,
    is_active      BOOLEAN DEFAULT TRUE,
    created_at     TIMESTAMP,
    updated_at     TIMESTAMP
);

-- Study sessions table
CREATE TABLE study_session (
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT REFERENCES users (id) ON DELETE CASCADE,
    start_time    TIMESTAMP,
    end_time      TIMESTAMP,
    points_earned DOUBLE PRECISION DEFAULT 0
);

-- Music saved by users
CREATE TABLE music_saved (
    id       BIGSERIAL PRIMARY KEY,
    user_id  BIGINT REFERENCES users (id) ON DELETE CASCADE,
    music_id BIGINT REFERENCES music (id) ON DELETE CASCADE,
    saved_at TIMESTAMP,
    CONSTRAINT uq_music_saved UNIQUE (user_id, music_id)
);

-- Music unlocked by users
CREATE TABLE music_unlocked (
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT REFERENCES users (id) ON DELETE CASCADE,
    music_id    BIGINT REFERENCES music (id) ON DELETE CASCADE,
    points_used BIGINT,
    unlocked_at TIMESTAMP,
    CONSTRAINT uq_music_unlocked UNIQUE (user_id, music_id)
);

-- Background saved by users
CREATE TABLE background_saved (
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT REFERENCES users (id) ON DELETE CASCADE,
    background_id BIGINT REFERENCES backgrounds (id) ON DELETE CASCADE,
    saved_at      TIMESTAMP,
    CONSTRAINT uq_background_saved UNIQUE (user_id, background_id)
);

-- Background unlocked by users
CREATE TABLE background_unlocked (
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT REFERENCES users (id) ON DELETE CASCADE,
    background_id BIGINT REFERENCES backgrounds (id) ON DELETE CASCADE,
    points_used   BIGINT,
    unlocked_at   TIMESTAMP,
    CONSTRAINT uq_background_unlocked UNIQUE (user_id, background_id)
);
