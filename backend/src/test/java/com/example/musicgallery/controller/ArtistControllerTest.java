package com.example.musicgallery.controller;

import com.example.musicgallery.model.Artist;
import com.example.musicgallery.service.ArtistService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistService artistService;

    @Autowired
    private TestRestTemplate restTemplate;

    private Artist artist1;
    private Artist artist2;
    private Artist testArtist;

    @BeforeEach
    public void setup() {

        artist1 = new Artist();
        artist1.setId(1);
        artist1.setName("Adele");
        artist1.setCountry("UK");

        artist2 = new Artist();
        artist2.setId(2);
        artist2.setName("Kanye West");
        artist2.setCountry("USA");

        testArtist = new Artist();
        testArtist.setId(1);
        testArtist.setName("Test Artist");
        testArtist.setCountry("USA");
    }

    @Test
    void testGetAllArtist() throws Exception {

        List<Artist> artistList = Arrays.asList(
                artist1,
                artist2
        );
        Mockito.when(artistService.getAllArtist()).thenReturn(artistList);

        mockMvc.perform(get("/artist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Adele")))
                .andExpect(jsonPath("$[0].country", is("UK")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Kanye West")))
                .andExpect(jsonPath("$[1].country", is("USA")));
    }

    @Test
    void testGetArtistById() throws Exception{

        Mockito.when(artistService.getArtistById(1)).thenReturn(testArtist);

        mockMvc.perform(get("/artist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Artist")))
                .andExpect(jsonPath("$.country", is("USA")));
    }

    @Test
    void testCreateArtist() throws Exception{

        Mockito.when(artistService.createArtist(Mockito.any(Artist.class))).thenReturn(testArtist);

        mockMvc.perform(post("/artist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Test Artist\", \"country\": \"USA\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testArtist.getId()))
                .andExpect(jsonPath("$.name").value("Test Artist"))
                .andExpect(jsonPath("$.country").value("USA"));

        verify(artistService, Mockito.times(1)).createArtist(Mockito.any(Artist.class));
    }

    @Test
    void testRemoveArtistById() throws Exception{

        Mockito.when(artistService.getById(Mockito.anyInt())).thenReturn(testArtist);

        mockMvc.perform(delete("/artist/{id}", testArtist.getId()))
                .andExpect(status().isOk());

        Mockito.verify(artistService, Mockito.times(1)).removeArtistById(Mockito.anyInt());
    }

    @Test
    public void testInvalidRequest() throws Exception {
        // create an invalid request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"country\": \"UK\"}", headers);

        // send post request
        ResponseEntity<String> response = restTemplate.postForEntity("/artist", request, String.class);

        // check status code & message
        assertEquals("Name cannot be empty", HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}