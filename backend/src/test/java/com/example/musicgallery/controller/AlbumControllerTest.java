package com.example.musicgallery.controller;

import com.example.musicgallery.service.*;
import org.junit.jupiter.api.Test;

import com.example.musicgallery.enums.AlbumType;
import com.example.musicgallery.model.Album;
import com.example.musicgallery.model.Artist;
import com.example.musicgallery.model.Music;
import org.junit.jupiter.api.BeforeEach;
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

import java.sql.Date;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AlbumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MusicService musicService;

    @Autowired
    private ArtistService artistService;

    @MockBean
    private AlbumService albumService;

    @MockBean
    private DigitalAlbumService digitalAlbumService;

    @MockBean
    private LiveAlbumService liveAlbumService;

    @Autowired
    private TestRestTemplate restTemplate;

    private Music song1;
    private Music song2;
    private Artist testArtist;
    private Artist testArtist2;
    private Album testAlbum;
    private Album testDigitalAlbum;
    private Album testLiveAlbum;

    @BeforeEach
    public void setup() {

        testArtist = new Artist();
        testArtist.setId(1);
        testArtist.setName("Adele");

        testArtist2 = new Artist();
        testArtist2.setId(2);
        testArtist2.setName("Kanye West");

        testAlbum = new Album();
        testAlbum.setId(1);
        testAlbum.setTitle("25");
        testAlbum.setType(1);
        testAlbum.setArtistId(testArtist.getId());
        testAlbum.setReleaseDate(Date.valueOf("2022-12-31"));

        testDigitalAlbum = new Album();
        testDigitalAlbum.setId(2);
        testDigitalAlbum.setTitle("30");
        testDigitalAlbum.setType(2);
        testDigitalAlbum.setArtistId(testArtist.getId());
        testDigitalAlbum.setReleaseDate(Date.valueOf("2022-12-31"));
        testDigitalAlbum.setPlatform("spotify");

        testLiveAlbum = new Album();
        testLiveAlbum.setId(3);
        testLiveAlbum.setTitle("Live Album");
        testLiveAlbum.setType(3);
        testLiveAlbum.setArtistId(testArtist2.getId());
        testLiveAlbum.setReleaseDate(Date.valueOf("2022-12-31"));
        testLiveAlbum.setVenue("USA");
        testLiveAlbum.setYear(Year.of(2019));

        song1 = new Music();
        song1.setId(1);
        song1.setTitle("Hello");
        song1.setDuration(120);
        song1.setArtistId(testArtist.getId());
        song1.setAlbumId(testAlbum.getId());

        song2 = new Music();
        song2.setId(2);
        song2.setTitle("When we were young");
        song2.setDuration(240);
        song2.setArtistId(testArtist.getId());
        song2.setAlbumId(testAlbum.getId());
    }

    @Test
    void testGetAlbumList() throws Exception{

        List<Album> albumList = Arrays.asList(
                testAlbum,
                testDigitalAlbum,
                testLiveAlbum
        );
        Mockito.when(albumService.getAlbumList()).thenReturn(albumList);

        mockMvc.perform(get("/album"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(testAlbum.getId())))
                .andExpect(jsonPath("$[0].title", is("25")))
                .andExpect(jsonPath("$[0].type", is(1)))
                .andExpect(jsonPath("$[0].artistId", is(testArtist.getId())))
                .andExpect(jsonPath("$[0].releaseDate", is("2022-12-31")))
                .andExpect(jsonPath("$[1].id", is(testDigitalAlbum.getId())))
                .andExpect(jsonPath("$[1].title", is("30")))
                .andExpect(jsonPath("$[1].type", is(2)))
                .andExpect(jsonPath("$[1].artistId", is(testArtist.getId())))
                .andExpect(jsonPath("$[1].releaseDate", is("2022-12-31")))
                .andExpect(jsonPath("$[1].platform", is("spotify")))
                .andExpect(jsonPath("$[2].id", is(testLiveAlbum.getId())))
                .andExpect(jsonPath("$[2].title", is("Live Album")))
                .andExpect(jsonPath("$[2].type", is(3)))
                .andExpect(jsonPath("$[2].artistId", is(testArtist2.getId())))
                .andExpect(jsonPath("$[2].releaseDate", is("2022-12-31")))
                .andExpect(jsonPath("$[2].venue", is("USA")))
                .andExpect(jsonPath("$[2].year", is("2019")));

    }

    @Test
    void testGetAlbumById() throws Exception{

        Mockito.when(albumService.getAlbumById(1)).thenReturn(testAlbum);

        mockMvc.perform(get("/album/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testAlbum.getId())))
                .andExpect(jsonPath("$.title", is("25")))
                .andExpect(jsonPath("$.type", is(1)))
                .andExpect(jsonPath("$.artistId", is(testArtist.getId())))
                .andExpect(jsonPath("$.releaseDate", is("2022-12-31")));
    }

    @Test
    void testGetAllAlbumByTitle() throws Exception{

        List<Album> albumList = Arrays.asList(
                testAlbum
        );

        Mockito.when(albumService.getAllAlbumByTitle(Mockito.anyString())).thenReturn(albumList);

        mockMvc.perform(get("/album/title").param("title", "25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(testAlbum.getId())))
                .andExpect(jsonPath("$[0].title", is("25")))
                .andExpect(jsonPath("$[0].type", is(1)))
                .andExpect(jsonPath("$[0].artistId", is(testArtist.getId())))
                .andExpect(jsonPath("$[0].releaseDate", is("2022-12-31")));

    }

    @Test
    void testGetAllAlbumByVenue() throws Exception{

        List<Album> albumList = Arrays.asList(
                testLiveAlbum
        );

        Mockito.when(liveAlbumService.getAllAlbumByVenue(Mockito.anyString())).thenReturn(albumList);

        mockMvc.perform(get("/album/venue").param("venue", "Album"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(testLiveAlbum.getId())))
                .andExpect(jsonPath("$[0].title", is("Live Album")))
                .andExpect(jsonPath("$[0].type", is(3)))
                .andExpect(jsonPath("$[0].artistId", is(testArtist2.getId())))
                .andExpect(jsonPath("$[0].releaseDate", is("2022-12-31")))
                .andExpect(jsonPath("$[0].venue", is("USA")))
                .andExpect(jsonPath("$[0].year", is("2019")));
    }

    @Test
    void testGetAllAlbumTypes() throws Exception{

        Mockito.when(albumService.getAllAlbumTypes()).thenReturn(AlbumType.getAllAlbumTypes());

        mockMvc.perform(get("/album/type"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.1", is("PHYSICAL")))
                .andExpect(jsonPath("$.2", is("DIGITAL")))
                .andExpect(jsonPath("$.3", is("LIVE")));
    }

    @Test
    void testCreateAlbumByType() throws Exception{

        Mockito.when(albumService.createAlbum(Mockito.any(Album.class))).thenReturn(testAlbum);
        Mockito.when(digitalAlbumService.createAlbum(Mockito.any(Album.class))).thenReturn(testDigitalAlbum);
        Mockito.when(liveAlbumService.createAlbum(Mockito.any(Album.class))).thenReturn(testLiveAlbum);

        mockMvc.perform(post("/album")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"25\", \"type\": \"1\", \"artistId\": " + testArtist.getId() + ", \"releaseDate\": \"2022-12-31\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testAlbum.getId())))
                .andExpect(jsonPath("$.title", is("25")))
                .andExpect(jsonPath("$.type", is(1)))
                .andExpect(jsonPath("$.artistId", is(testArtist.getId())))
                .andExpect(jsonPath("$.releaseDate", is("2022-12-31")));

        verify(albumService, Mockito.times(1)).createAlbum(Mockito.any(Album.class));

        mockMvc.perform(post("/album")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"30\", \"type\": 2, \"artistId\": " + testArtist.getId() + ", \"releaseDate\": \"2022-12-31\", \"platform\": \"spotify\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testDigitalAlbum.getId())))
                .andExpect(jsonPath("$.title", is("30")))
                .andExpect(jsonPath("$.type", is(2)))
                .andExpect(jsonPath("$.artistId", is(testArtist.getId())))
                .andExpect(jsonPath("$.releaseDate", is("2022-12-31")))
                .andExpect(jsonPath("$.platform", is("spotify")));

        verify(digitalAlbumService, Mockito.times(1)).createAlbum(Mockito.any(Album.class));

        mockMvc.perform(post("/album")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Live Album\", \"type\": 3, \"artistId\": " + testArtist2.getId() + ", \"releaseDate\": \"2022-12-31\", \"venue\": \"USA\", \"year\": \"2019\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testLiveAlbum.getId())))
                .andExpect(jsonPath("$.title", is("Live Album")))
                .andExpect(jsonPath("$.type", is(3)))
                .andExpect(jsonPath("$.artistId", is(testArtist2.getId())))
                .andExpect(jsonPath("$.releaseDate", is("2022-12-31")))
                .andExpect(jsonPath("$.venue", is("USA")))
                .andExpect(jsonPath("$.year", is("2019")));

        verify(liveAlbumService, Mockito.times(1)).createAlbum(Mockito.any(Album.class));

    }

    @Test
    void testUpdateAlbum() throws Exception{

        Mockito.when(albumService.updateAlbum(Mockito.any(Album.class))).thenReturn(testAlbum);
        Mockito.when(digitalAlbumService.updateAlbum(Mockito.any(Album.class))).thenReturn(testDigitalAlbum);
        Mockito.when(liveAlbumService.updateAlbum(Mockito.any(Album.class))).thenReturn(testLiveAlbum);

        mockMvc.perform(put("/album/{id}", testAlbum.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"title\": \"25\", \"type\": \"1\", \"artistId\": " + testArtist.getId() + ", \"releaseDate\": \"2022-12-31\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testAlbum.getId())))
                .andExpect(jsonPath("$.title", is("25")))
                .andExpect(jsonPath("$.type", is(1)))
                .andExpect(jsonPath("$.artistId", is(testArtist.getId())))
                .andExpect(jsonPath("$.releaseDate", is("2022-12-31")));

        Mockito.verify(albumService, Mockito.times(1)).updateAlbum(Mockito.any(Album.class));

        mockMvc.perform(put("/album/{id}", testDigitalAlbum.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"30\", \"type\": 2, \"artistId\": " + testArtist.getId() + ", \"releaseDate\": \"2022-12-31\", \"platform\": \"spotify\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testDigitalAlbum.getId())))
                .andExpect(jsonPath("$.title", is("30")))
                .andExpect(jsonPath("$.type", is(2)))
                .andExpect(jsonPath("$.artistId", is(testArtist.getId())))
                .andExpect(jsonPath("$.releaseDate", is("2022-12-31")))
                .andExpect(jsonPath("$.platform", is("spotify")));

        Mockito.verify(digitalAlbumService, Mockito.times(1)).updateAlbum(Mockito.any(Album.class));

        mockMvc.perform(put("/album/{id}", testLiveAlbum.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Live Album\", \"type\": 3, \"artistId\": " + testArtist2.getId() + ", \"releaseDate\": \"2022-12-31\", \"venue\": \"USA\", \"year\": \"2019\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testLiveAlbum.getId())))
                .andExpect(jsonPath("$.title", is("Live Album")))
                .andExpect(jsonPath("$.type", is(3)))
                .andExpect(jsonPath("$.artistId", is(testArtist2.getId())))
                .andExpect(jsonPath("$.releaseDate", is("2022-12-31")))
                .andExpect(jsonPath("$.venue", is("USA")))
                .andExpect(jsonPath("$.year", is("2019")));

        Mockito.verify(liveAlbumService, Mockito.times(1)).updateAlbum(Mockito.any(Album.class));

    }

    @Test
    void testRemoveAlbumById() throws Exception{

        Mockito.when(albumService.getById(Mockito.anyInt())).thenReturn(testAlbum);

        mockMvc.perform(delete("/album/{id}", testAlbum.getId()))
                .andExpect(status().isOk());

        Mockito.verify(albumService, Mockito.times(1)).removeAlbumById(Mockito.anyInt());

    }

    @Test
    public void testInvalidRequest() throws Exception {

        // create an invalid request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"title\": \"foo\"}", headers);

        // send post request
        ResponseEntity<String> response = restTemplate.postForEntity("/album", request, String.class);

        // check status code & message
        assertEquals("ArtistId cannot be null", HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}