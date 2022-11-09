package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.ArtistRecommendation;
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
public class ArtistRecommendationRepositoryTest {

    @Autowired
    private ArtistRecommendationRepository repo;
    @Before
    public void setUp()throws Exception{

    }
    @Test
    public void shouldInteractWithDatabase(){
        ArtistRecommendation artistRecommendation =new ArtistRecommendation(1, 1, true);
        ArtistRecommendation expectedArtistRecommendation =new ArtistRecommendation(1, 1, true);
        repo.save(artistRecommendation);
        expectedArtistRecommendation.setId(artistRecommendation.getId());
        assertEquals(expectedArtistRecommendation, artistRecommendation);

        // Act
        List<ArtistRecommendation> allTheArtistRecommendation = repo.findAll();

        // Assert
        assertEquals(1, allTheArtistRecommendation.size());

        // Act
        repo.deleteById(artistRecommendation.getId());

        allTheArtistRecommendation = repo.findAll();
        assertEquals(0, allTheArtistRecommendation.size());
    }


}