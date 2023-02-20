package com.example.musicgallery.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.musicgallery.enums.AlbumType;
import com.example.musicgallery.model.Album;
import com.example.musicgallery.service.LiveAlbumService;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Locale;

@Service
public class LiveAlbumServiceImpl extends AlbumServiceImpl implements LiveAlbumService {

    @Override
    public Album createAlbum(Album album) {
        if (isInvalidLiveAlbum(album)) {
            throw new RuntimeException("LiveAlbum fields are incomplete");
        }
        return super.createAlbum(album);
    }

    @Override
    public Album updateAlbum(Album album) {
        if (isInvalidLiveAlbum(album)) {
            throw new RuntimeException("DigitalAlbum fields are incomplete");
        }
        return super.updateAlbum(album);
    };

    public List<Album> getAllAlbumByVenue(String venue) {
        LambdaQueryWrapper<Album> albumLambdaQueryWrapper = Wrappers.lambdaQuery(Album.class)
                .eq(Album::getType, AlbumType.getType(AlbumType.LIVE.name()));
        albumLambdaQueryWrapper.like(Album::getVenue, venue);
        return this.list(albumLambdaQueryWrapper);
    }

    private boolean isInvalidLiveAlbum(Album album) {
        String venue = album.getVenue();
        Year year = album.getYear();
        return null == venue || venue.isEmpty() || null == year;
    };
}
