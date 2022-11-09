package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.repository.LabelRecommendationRepository;
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
@WebMvcTest(LabelRecommendationController.class)
public class LabelRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LabelRecommendationRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private LabelRecommendation helloLabelRecommendation;
    private String helloJson;
    private List<LabelRecommendation> allLabelRecommendations = new ArrayList<>();
    private String allLabelRecommendationsJson;

    @Before
    public void setup() throws Exception {
        helloLabelRecommendation = new LabelRecommendation(1,1,1,true);
        helloJson = mapper.writeValueAsString(helloLabelRecommendation);

        LabelRecommendation LabelRecommendation = new LabelRecommendation(1,1,true);
        LabelRecommendation.setId(36);
        allLabelRecommendations.add(helloLabelRecommendation);
        allLabelRecommendations.add(LabelRecommendation);

        allLabelRecommendationsJson = mapper.writeValueAsString(allLabelRecommendations);
    }

    @Test
    public void shouldCreateNewLabelRecommendationOnPostRequest() throws Exception {
        LabelRecommendation inputLabelRecommendation = new LabelRecommendation(1,1,true);
        inputLabelRecommendation.setId(36);
        String inputJson = mapper.writeValueAsString(inputLabelRecommendation);

        doReturn(helloLabelRecommendation).when(repo).save(inputLabelRecommendation);

        mockMvc.perform(post("/labelRecommendation")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(helloJson));

    }

    @Test
    public void shouldReturnLabelRecommendationById() throws Exception {
        doReturn(Optional.of(helloLabelRecommendation)).when(repo).findById(1010);

        mockMvc.perform(get("/labelRecommendation/1010"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(helloJson));

    }

    @Test
    public void shouldThrowBadExceptionLabelRecommendationId() throws Exception {
        doThrow(new BadIdException()).when(repo).findById(2022);

        mockMvc.perform(get("/labelRecommendation/2022"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnAllLabelRecommendations() throws Exception {
        doReturn(allLabelRecommendations).when(repo).findAll();

        mockMvc.perform(get("/labelRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(allLabelRecommendationsJson)
                );
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        LabelRecommendation outputLabelRecommendation = new LabelRecommendation();
        outputLabelRecommendation.setLabelId(1);
        outputLabelRecommendation.setUserId(1);
        outputLabelRecommendation.setLiked(true);
        outputLabelRecommendation.setId(88);

        String outputJson = mapper.writeValueAsString(outputLabelRecommendation);

        mockMvc.perform(put("/labelRecommendation/88")
                .content(outputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/labelRecommendation/22")).andExpect(status().isNoContent());
    }

}