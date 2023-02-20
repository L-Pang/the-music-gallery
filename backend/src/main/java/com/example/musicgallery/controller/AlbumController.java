package com.example.musicgallery.controller;

import com.example.musicgallery.enums.AlbumType;
import com.example.musicgallery.model.Album;
import com.example.musicgallery.service.AlbumService;
import com.example.musicgallery.service.DigitalAlbumService;
import com.example.musicgallery.service.LiveAlbumService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/album")
@Validated
public class AlbumController {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private DigitalAlbumService digitalAlbumService;
    @Autowired
    private LiveAlbumService liveAlbumService;


    @GetMapping
    public List<Album> getAlbumList() {
        return albumService.getAlbumList();
    }

    @GetMapping("/{albumId}")
    public Album getAlbumById(@PathVariable Integer albumId) {
        return albumService.getAlbumById(albumId);
    }

    @GetMapping("/title")
    public List<Album> getAllAlbumByTitle(@RequestParam String title) {
        return albumService.getAllAlbumByTitle(title);
    }

    @GetMapping("/venue")
    public List<Album> getAllAlbumByVenue(@RequestParam String venue) {
        return liveAlbumService.getAllAlbumByVenue(venue);
    }

    @GetMapping("/type")
    public Map<Integer, String> getAllAlbumTypes() {
        return albumService.getAllAlbumTypes();
    }

    @PostMapping
    public Album createAlbumByType(@Valid @RequestBody Album album) {

        Integer type = album.getType();
        if (type.equals(AlbumType.DIGITAL.getType())) {
            return digitalAlbumService.createAlbum(album);
        } else if (type.equals(AlbumType.LIVE.getType())) {
            return liveAlbumService.createAlbum(album);
        }
        return albumService.createAlbum(album);
    }

    @PutMapping("/{albumId}")
    public Album updateAlbum(@Valid @RequestBody Album album) {

        Integer type = album.getType();
        if (type.equals(AlbumType.DIGITAL.getType())) {
            return digitalAlbumService.updateAlbum(album);
        } else if (type.equals(AlbumType.LIVE.getType())) {
            return liveAlbumService.updateAlbum(album);
        }
        return albumService.updateAlbum(album);
    }

    @DeleteMapping("/{albumId}")
    public void removeAlbumById(@PathVariable Integer albumId) throws NotFoundException {
        albumService.removeAlbumById(albumId);
    }
}
