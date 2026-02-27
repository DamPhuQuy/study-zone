package com.studentzone.app.music.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.studentzone.app.music.dto.mapper.MusicMapper;
import com.studentzone.app.music.dto.response.MusicSavedResponseDTO;
import com.studentzone.app.music.dto.response.MusicUnlockedResponseDTO;
import com.studentzone.app.music.entity.MusicEntity;
import com.studentzone.app.music.entity.MusicSavedEntity;
import com.studentzone.app.music.entity.MusicUnlockedEntity;
import com.studentzone.app.music.repository.MusicRepository;
import com.studentzone.app.music.repository.MusicSavedRepository;
import com.studentzone.app.music.repository.MusicUnlockedRepository;
import com.studentzone.app.user.entity.UserEntity;
import com.studentzone.app.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class MusicService {
    private final UserRepository userRepository;
    private final MusicRepository musicRepository;
    private final MusicSavedRepository musicSavedRepository;
    private final MusicUnlockedRepository musicUnlockedRepository;

    public MusicService(UserRepository userRepository,
                        MusicRepository musicRepository,
                        MusicSavedRepository musicSavedRepository,
                        MusicUnlockedRepository musicUnlockedRepository) {
        this.userRepository = userRepository;
        this.musicRepository = musicRepository;
        this.musicSavedRepository = musicSavedRepository;
        this.musicUnlockedRepository = musicUnlockedRepository;
    }

    @Transactional
    public MusicSavedResponseDTO saveMusic(Long userId, Long musicId) {
        MusicEntity music = musicRepository.findById(musicId)
                .orElseThrow(() -> new IllegalArgumentException("Music not found with id: " + musicId));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        MusicSavedEntity musicSaved = MusicSavedEntity.builder()
                                                      .user(user)
                                                      .music(music)
                                                      .savedAt(LocalDateTime.now())
                                                      .build();
        return MusicMapper.toSavedDto(musicSavedRepository.save(musicSaved));
    }

    @Transactional
    public MusicUnlockedResponseDTO unlockMusic(Long userId, Long musicId) {
        MusicEntity music = musicRepository.findById(musicId)
                .orElseThrow(() -> new IllegalArgumentException("Music not found with id: " + musicId));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        MusicUnlockedEntity musicUnlocked = MusicUnlockedEntity.builder()
                                                               .user(user)
                                                               .music(music)
                                                               .unlockedAt(LocalDateTime.now())
                                                               .build();
        // user spent points to unlock
        user.setTotalPoints(user.getTotalPoints() - music.getRequiredPoint());
        userRepository.save(user);

        return MusicMapper.toUnlockedDto(musicUnlockedRepository.save(musicUnlocked));
    }

    public List<MusicSavedResponseDTO> getSavedMusicByUserId(Long userId) {
        return musicSavedRepository.findByUserId(userId).stream()
                                  .map(MusicMapper::toSavedDto)
                                  .toList();
    }

    public List<MusicUnlockedResponseDTO> getUnlockedMusicByUserId(Long userId) {
        return musicUnlockedRepository.findByUserId(userId).stream()
                                     .map(MusicMapper::toUnlockedDto)
                                     .toList();
    }

    @Transactional
    public void deleteMusic(Long musicId) {
        MusicEntity music = musicRepository.findById(musicId)
                .orElseThrow(() -> new IllegalArgumentException("Music not found with id: " + musicId));

        musicRepository.delete(music);
    }

    @Transactional
    public void deleteSavedMusic(Long userId, Long musicId) {
        MusicSavedEntity musicSavedEntity = musicSavedRepository.findByUserIdAndMusicId(userId, musicId)
                .orElseThrow(() -> new IllegalArgumentException("Saved music not found for userId: " + userId + " and musicId: " + musicId));

        if (musicSavedEntity == null) {
            throw new IllegalArgumentException("Saved music not found for userId: " + userId + " and musicId: " + musicId);
        }

        musicSavedRepository.delete(musicSavedEntity);
    }
}
