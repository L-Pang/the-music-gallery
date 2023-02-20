package com.example.musicgallery.service;

import com.example.musicgallery.enums.AlbumType;
import com.example.musicgallery.model.Album;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musicgallery.model.Artist;
import com.example.musicgallery.model.Music;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.data.relational.core.sql.In;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author Lin Pang
 * @since 2023-02-17
 */
public interface AlbumService extends IService<Album> {

    public Album createAlbum(Album album);

    public void removeAlbumById (Integer albumId) throws NotFoundException;

    public Album getAlbumById(Integer albumId);

    public List<Album> getAlbumList();

    public List<Album> getAllAlbumByTitle(String title);

    public Album updateAlbum(Album album);

    public Map<Integer, String> getAllAlbumTypes();
}
