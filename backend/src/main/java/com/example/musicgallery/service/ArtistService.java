package com.example.musicgallery.service;

import com.example.musicgallery.model.Album;
import com.example.musicgallery.model.Artist;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musicgallery.model.Music;
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
public interface ArtistService extends IService<Artist> {

    public Artist createArtist(Artist artist);

    public void removeArtistById (Integer artistId) throws NotFoundException;

    public Artist getArtistById(Integer artistId);

    public List<Artist> getAllArtist();
}
