package com.example.musicgallery.service;

import com.example.musicgallery.model.Album;

import java.util.List;

public interface LiveAlbumService extends AlbumService{

    public List<Album> getAllAlbumByVenue(String venue);
}
