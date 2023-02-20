package com.example.musicgallery.controller;

import com.example.musicgallery.enums.AlbumType;
import com.example.musicgallery.model.Album;
import com.example.musicgallery.model.Artist;
import com.example.musicgallery.model.Music;
import com.example.musicgallery.service.AlbumService;
import com.example.musicgallery.service.ArtistService;
import com.example.musicgallery.service.MusicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MusicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicService musicService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private TestRestTemplate restTemplate;

    private Artist testArtist;
    private Album testAlbum;
    private Music song1;
    private Music song2;
    private Music testMusic;

    @BeforeEach
    public void setup() {

        testArtist = new Artist();
        testArtist.setId(1);
        testArtist.setName("Test Artist");

        testAlbum = new Album();
        testAlbum.setId(1);
        testAlbum.setTitle("Test Album");
        testAlbum.setArtistId(testArtist.getId());
        testAlbum.setType(AlbumType.PHYSICAL.getType());
        testAlbum.setReleaseDate(new Date());

        song1 = new Music();
        song1.setId(1);
        song1.setTitle("Song 1");
        song1.setDuration(120);
        song1.setArtistId(testArtist.getId());
        song1.setAlbumId(testAlbum.getId());

        song2 = new Music();
        song2.setId(2);
        song2.setTitle("Song 2");
        song2.setDuration(240);
        song2.setArtistId(testArtist.getId());
        song2.setAlbumId(testAlbum.getId());

        testMusic = new Music();
        testMusic.setId(1);
        testMusic.setTitle("Test music");
        testMusic.setDuration(360);
        testMusic.setArtistId(testArtist.getId());
        testMusic.setAlbumId(testAlbum.getId());

    }


    @Test
    public void testGetAllMusic() throws Exception {

        List<Music> musicList = Arrays.asList(
                song1,
                song2
        );
        Mockito.when(musicService.getAllMusic()).thenReturn(musicList);

        mockMvc.perform(get("/music"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Song 1")))
                .andExpect(jsonPath("$[0].duration", is(120)))
                .andExpect(jsonPath("$[0].artistId", is(testArtist.getId())))
                .andExpect(jsonPath("$[0].albumId", is(testAlbum.getId())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Song 2")))
                .andExpect(jsonPath("$[1].duration", is(240)))
                .andExpect(jsonPath("$[1].artistId", is(testArtist.getId())))
                .andExpect(jsonPath("$[1].albumId", is(testAlbum.getId())));
    }

    @Test
    public void testGetMusicById() throws Exception {

        Mockito.when(musicService.getMusicById(1)).thenReturn(testMusic);

        mockMvc.perform(get("/music/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test music")))
                .andExpect(jsonPath("$.duration", is(360)))
                .andExpect(jsonPath("$.artistId", is(testArtist.getId())))
                .andExpect(jsonPath("$.albumId", is(testAlbum.getId())));
    }

    @Test
    void testGetMusicListByAlbumId() throws Exception {

        List<Music> musicList = Arrays.asList(
                song1,
                song2
        );

        Mockito.when(musicService.getMusicListByAlbumId(1)).thenReturn(musicList);

        mockMvc.perform(get("/music/list/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Song 1")))
                .andExpect(jsonPath("$[0].duration", is(120)))
                .andExpect(jsonPath("$[0].artistId", is(testArtist.getId())))
                .andExpect(jsonPath("$[0].albumId", is(testAlbum.getId())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Song 2")))
                .andExpect(jsonPath("$[1].duration", is(240)))
                .andExpect(jsonPath("$[1].artistId", is(testArtist.getId())))
                .andExpect(jsonPath("$[1].albumId", is(testAlbum.getId())));

    }

    @Test
    void testCreateMusic() throws Exception{

        Mockito.when(musicService.createMusic(Mockito.any(Music.class))).thenReturn(testMusic);

        mockMvc.perform(post("/music")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Test music\", \"duration\": \"360\", \"artistId\": " + testArtist.getId() + ", \"albumId\": " + testAlbum.getId() + " }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testMusic.getId()))
                .andExpect(jsonPath("$.title").value(testMusic.getTitle()))
                .andExpect(jsonPath("$.duration").value(360))
                .andExpect(jsonPath("$.artistId").value(testArtist.getId()))
                .andExpect(jsonPath("$.albumId").value(testAlbum.getId()));

        verify(musicService, Mockito.times(1)).createMusic(Mockito.any(Music.class));

    }

    @Test
    public void testRemoveMusicById() throws Exception {

        Mockito.when(musicService.getById(Mockito.anyInt())).thenReturn(testMusic);

        mockMvc.perform(delete("/music/{id}", testMusic.getId()))
                .andExpect(status().isOk());

        Mockito.verify(musicService, Mockito.times(1)).removeMusicById(Mockito.anyInt());
    }

    @Test
    public void testInvalidRequest() throws Exception {
        // create an invalid request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"title\": \"foo\"}", headers);

        // send post request
        ResponseEntity<String> response = restTemplate.postForEntity("/music", request, String.class);

        // check status code & message
        assertEquals("Duration cannot be null", HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}