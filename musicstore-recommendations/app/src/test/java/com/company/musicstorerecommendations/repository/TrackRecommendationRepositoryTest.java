package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.TrackRecommendation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TrackRecommendationRepositoryTest {

    @Autowired
    private TrackRecommendationRepository repo;
    @Before
    public void setUp()throws Exception{

    }
    @Test
    public void shouldInteractWithDatabase(){
        TrackRecommendation trackRecommendation =new TrackRecommendation(1, 1, true);
        TrackRecommendation expectedTrackRecommendation =new TrackRecommendation(1, 1, true);
        repo.save(trackRecommendation);
        expectedTrackRecommendation.setId(trackRecommendation.getId());
        assertEquals(expectedTrackRecommendation, trackRecommendation);

        // Act
        List<TrackRecommendation> allTheTrackRecommendation = repo.findAll();

        // Assert
        assertEquals(1, allTheTrackRecommendation.size());

        // Act
        repo.deleteById(trackRecommendation.getId());

        allTheTrackRecommendation = repo.findAll();
        assertEquals(0, allTheTrackRecommendation.size());
    }
}