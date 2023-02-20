package com.example.musicgallery.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.musicgallery.enums.AlbumType;
import com.example.musicgallery.model.Album;
import com.example.musicgallery.mapper.AlbumMapper;
import com.example.musicgallery.service.AlbumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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
@Primary
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {

    @Override
    public Album createAlbum(Album album) {
        boolean res = this.save(album);
        System.out.println("line 34 " + res);
        if (!res) {
            throw new RuntimeException("Add album failed");
        }
        return album;
    }

    @Override
    public void removeAlbumById(Integer albumId) throws NotFoundException {
        Album album = this.getById(albumId);
        if (null == album) {
            throw new NotFoundException("Album not found");
        }
        this.removeById(album.getId());
    }

    @Override
    public Album getAlbumById(Integer albumId) {
        return this.getById(albumId);
    }

    @Override
    public List<Album> getAlbumList() {
        LambdaQueryWrapper<Album> albumLambdaQueryWrapper = Wrappers.lambdaQuery(Album.class);
        return this.list(albumLambdaQueryWrapper);
    }

    @Override
    public List<Album> getAllAlbumByTitle(String title) {
        LambdaQueryWrapper<Album> albumLambdaQueryWrapper = Wrappers.lambdaQuery(Album.class);
        albumLambdaQueryWrapper.like(Album::getTitle, title);
        return this.list(albumLambdaQueryWrapper);
    }

    @Override
    public Album updateAlbum(Album album) {
        boolean res = this.updateById(album);
        if (!res) {
            throw new RuntimeException("Update album failed");
        }
        return album;
    }

    @Override
    public Map<Integer, String> getAllAlbumTypes() {
        return AlbumType.getAllAlbumTypes();
    }
}
