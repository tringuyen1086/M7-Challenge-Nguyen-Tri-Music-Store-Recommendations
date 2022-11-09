package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.repository.AlbumRecommendationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumRecommendationRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private AlbumRecommendation helloAlbumRecommendation;
    private String helloJson;
    private List<AlbumRecommendation> allAlbumRecommendations = new ArrayList<>();
    private String allAlbumRecommendationsJson;

    @Before
    public void setup() throws Exception {
        helloAlbumRecommendation = new AlbumRecommendation(1,1,1,true);
        helloJson = mapper.writeValueAsString(helloAlbumRecommendation);

        AlbumRecommendation AlbumRecommendation = new AlbumRecommendation(1,1,true);
        AlbumRecommendation.setId(36);
        allAlbumRecommendations.add(helloAlbumRecommendation);
        allAlbumRecommendations.add(AlbumRecommendation);

        allAlbumRecommendationsJson = mapper.writeValueAsString(allAlbumRecommendations);
    }

    @Test
    public void shouldCreateNewAlbumRecommendationOnPostRequest() throws Exception {
        AlbumRecommendation inputAlbumRecommendation = new AlbumRecommendation(1,1,true);
        inputAlbumRecommendation.setId(36);
        String inputJson = mapper.writeValueAsString(inputAlbumRecommendation);

        doReturn(helloAlbumRecommendation).when(repo).save(inputAlbumRecommendation);

        mockMvc.perform(post("/albumRecommendation")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(helloJson));

    }

    @Test
    public void shouldReturnAlbumRecommendationById() throws Exception {
        doReturn(Optional.of(helloAlbumRecommendation)).when(repo).findById(1010);

        mockMvc.perform(get("/albumRecommendation/1010"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(helloJson));
    }

    @Test
    public void shouldThrowBadExceptionAlbumRecommendationId() throws Exception {
        doThrow(new BadIdException()).when(repo).findById(2022);

        mockMvc.perform(get("/albumRecommendation/2022"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnAllAlbumRecommendations() throws Exception {
        doReturn(allAlbumRecommendations).when(repo).findAll();

        mockMvc.perform(get("/albumRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(allAlbumRecommendationsJson));

    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        AlbumRecommendation outputAlbumRecommendation = new AlbumRecommendation();
        outputAlbumRecommendation.setAlbumId(1);
        outputAlbumRecommendation.setUserId(1);
        outputAlbumRecommendation.setLiked(true);
        outputAlbumRecommendation.setId(88);

        String outputJson = mapper.writeValueAsString(outputAlbumRecommendation);

        mockMvc.perform(put("/albumRecommendation/88")
                .content(outputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/albumRecommendation/22")).andExpect(status().isNoContent());
    }


}