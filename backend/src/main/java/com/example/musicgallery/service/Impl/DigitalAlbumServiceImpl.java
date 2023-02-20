package com.example.musicgallery.service.Impl;

import com.example.musicgallery.model.Album;
import com.example.musicgallery.service.DigitalAlbumService;
import org.springframework.stereotype.Service;

@Service
public class DigitalAlbumServiceImpl extends AlbumServiceImpl implements DigitalAlbumService {

    @Override
    public Album createAlbum(Album album) {
        if (isInvalidDigitalAlbum(album)) {
            throw new RuntimeException("DigitalAlbum fields are incomplete");
        }
        return super.createAlbum(album);
    }

    @Override
    public Album updateAlbum(Album album) {
        if (isInvalidDigitalAlbum(album)) {
            throw new RuntimeException("DigitalAlbum fields are incomplete");
        }
        return super.updateAlbum(album);
    };

    private boolean isInvalidDigitalAlbum(Album album) {
        String platform = album.getPlatform();
        return null == platform || platform.isEmpty();
    };
}
