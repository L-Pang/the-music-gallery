package com.example.musicgallery.controller;

import com.example.musicgallery.model.Artist;
import com.example.musicgallery.service.ArtistService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/artist")
@Validated
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping
    public List<Artist> getAllArtist() {
        return artistService.getAllArtist();
    }

    @GetMapping("/{artistId}")
    public Artist getArtistById(@PathVariable Integer artistId) {
        return artistService.getArtistById(artistId);
    }

    @PostMapping
    public Artist createArtist(@RequestBody @Valid Artist artist) {
        return artistService.createArtist(artist);
    }

    @DeleteMapping("/{artist}")
    public void removeArtistById(@PathVariable Integer artist) throws NotFoundException {
        artistService.removeArtistById(artist);
    }
}
