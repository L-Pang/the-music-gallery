package com.example.musicgallery.controller;

import com.example.musicgallery.model.Music;
import com.example.musicgallery.service.MusicService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/music")
@Validated
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping
    public List<Music> getAllMusic() {
        return musicService.getAllMusic();
    }

    @GetMapping("/{musicId}")
    public Music getMusicById(@PathVariable Integer musicId) {
        return musicService.getMusicById(musicId);
    }

    @GetMapping("/list/{albumId}")
    public List<Music> getMusicListByAlbumId(@PathVariable Integer albumId) {
        return musicService.getMusicListByAlbumId(albumId);
    }

    @PostMapping
    public Music createMusic(@RequestBody @Valid Music music){
        return musicService.createMusic(music);
    }

    @DeleteMapping("/{musicId}")
    public void removeMusicById(@PathVariable Integer musicId) throws NotFoundException {
        musicService.removeMusicById(musicId);
    }

}
