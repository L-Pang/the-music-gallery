package com.example.musicgallery.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.musicgallery.model.Album;
import com.example.musicgallery.model.Music;
import com.example.musicgallery.mapper.MusicMapper;
import com.example.musicgallery.service.MusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Lin Pang
 * @since 2023-02-17
 */
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {

    @Override
    public Music createMusic(Music music) {
        boolean res = this.save(music);
        if (!res) {
            throw new RuntimeException("Add music failed");
        }
        return music;
    }

    @Override
    public void removeMusicById(Integer musicId) throws NotFoundException {
        Music music = this.getById(musicId);
        if (null == music) {
            throw new NotFoundException("Music not found");
        }
        this.removeById(music.getId());
    }

    @Override
    public Music getMusicById(Integer musicId) {return this.getById(musicId);}

    @Override
    public List<Music> getMusicListByAlbumId(Integer albumId) {
        LambdaQueryWrapper<Music> albumLambdaQueryWrapper = Wrappers.lambdaQuery(Music.class)
                .eq(Music::getAlbumId, albumId);
        return this.list(albumLambdaQueryWrapper);
    }

    @Override
    public List<Music> getAllMusic() {
        LambdaQueryWrapper<Music> albumLambdaQueryWrapper = Wrappers.lambdaQuery(Music.class);
        return this.list(albumLambdaQueryWrapper);
    }

}
