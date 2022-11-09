package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.repository.TrackRecommendationRepository;
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
@WebMvcTest(TrackRecommendationController.class)
public class TrackRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackRecommendationRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private TrackRecommendation helloTrackRecommendation;
    private String helloJson;
    private List<TrackRecommendation> allTrackRecommendations = new ArrayList<>();
    private String allTrackRecommendationsJson;

    @Before
    public void setup() throws Exception {
        helloTrackRecommendation = new TrackRecommendation(1,1,1,true);
        helloJson = mapper.writeValueAsString(helloTrackRecommendation);

        TrackRecommendation TrackRecommendation = new TrackRecommendation(1,1,true);
        TrackRecommendation.setId(36);
        allTrackRecommendations.add(helloTrackRecommendation);
        allTrackRecommendations.add(TrackRecommendation);

        allTrackRecommendationsJson = mapper.writeValueAsString(allTrackRecommendations);
    }

    @Test
    public void shouldCreateNewTrackRecommendationOnPostRequest() throws Exception {
        TrackRecommendation inputTrackRecommendation = new TrackRecommendation(1,1,true);
        inputTrackRecommendation.setId(36);
        String inputJson = mapper.writeValueAsString(inputTrackRecommendation);

        doReturn(helloTrackRecommendation).when(repo).save(inputTrackRecommendation);

        mockMvc.perform(post("/trackRecommendation")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(helloJson));

    }

    @Test
    public void shouldReturnTrackRecommendationById() throws Exception {
        doReturn(Optional.of(helloTrackRecommendation)).when(repo).findById(1010);

        mockMvc.perform(get("/trackRecommendation/1010"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(helloJson));

    }

    @Test
    public void shouldThrowBadExceptionTrackRecommendationId() throws Exception {
        doThrow(new BadIdException()).when(repo).findById(2022);

        mockMvc.perform(get("/trackRecommendation/2022"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnAllTrackRecommendations() throws Exception {
        doReturn(allTrackRecommendations).when(repo).findAll();

        mockMvc.perform(get("/trackRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(allTrackRecommendationsJson));

    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        TrackRecommendation outputTrackRecommendation = new TrackRecommendation();
        outputTrackRecommendation.setTrackId(1);
        outputTrackRecommendation.setUserId(1);
        outputTrackRecommendation.setLiked(true);
        outputTrackRecommendation.setId(88);

        String outputJson = mapper.writeValueAsString(outputTrackRecommendation);

        mockMvc.perform(put("/trackRecommendation/88")
                .content(outputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/trackRecommendation/22")).andExpect(status().isNoContent());
    }

}