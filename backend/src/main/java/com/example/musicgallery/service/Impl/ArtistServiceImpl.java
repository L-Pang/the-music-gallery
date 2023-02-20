package com.example.musicgallery.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.musicgallery.model.Artist;
import com.example.musicgallery.mapper.ArtistMapper;
import com.example.musicgallery.model.Music;
import com.example.musicgallery.service.ArtistService;
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
public class ArtistServiceImpl extends ServiceImpl<ArtistMapper, Artist> implements ArtistService {

    @Override
    public Artist createArtist(Artist artist) {

        boolean res = this.save(artist);
        if (!res) {
            throw new RuntimeException("Add artist failed");
        }
        return artist;
    }

    @Override
    public void removeArtistById(Integer artistId) throws NotFoundException {
        Artist artist = this.getById(artistId);
        if (null == artist) {
            throw new NotFoundException("Artist not found");
        }
        this.removeById(artist.getId());
    }

    @Override
    public Artist getArtistById(Integer artistId) {return this.getById(artistId);}

    @Override
    public List<Artist> getAllArtist() {
        LambdaQueryWrapper<Artist> albumLambdaQueryWrapper = Wrappers.lambdaQuery(Artist.class);
        return this.list(albumLambdaQueryWrapper);
        }
}
