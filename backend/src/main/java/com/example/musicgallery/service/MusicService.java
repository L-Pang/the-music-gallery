package com.example.musicgallery.service;

import com.example.musicgallery.model.Artist;
import com.example.musicgallery.model.Music;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.javassist.NotFoundException;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Lin Pang
 * @since 2023-02-17
 */
public interface MusicService extends IService<Music> {

    public Music createMusic(Music music) ;

    public void removeMusicById (Integer musicId) throws NotFoundException;

    public Music getMusicById(Integer musicId);

    public List<Music> getMusicListByAlbumId(Integer albumId);

    public List<Music> getAllMusic();

}
