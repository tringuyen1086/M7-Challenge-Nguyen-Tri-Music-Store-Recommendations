package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.model.LabelRecommendation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlbumRecommendationRepositoryTest {
    @Autowired
    private AlbumRecommendationRepository repo;
    @Autowired
    private LabelRecommendationRepository labelRecommendationRepo;
    @Autowired
    private ArtistRecommendationRepository artistRecommendationRepo;

    @Before
    public void setUp()throws Exception{

        LabelRecommendation labelRecommendation1 =new LabelRecommendation(1, 1, true);
        LabelRecommendation expectedLabelRecommendation =new LabelRecommendation(1,1, true);
        labelRecommendationRepo.save(labelRecommendation1);
        ArtistRecommendation artistRecommendation1 =new ArtistRecommendation(1, 1, true);
        ArtistRecommendation expectedArtistRecommendation =new ArtistRecommendation(1,1, true);
        artistRecommendationRepo.save(artistRecommendation1);


    }
    @Test
    public void shouldInteractWithDatabase(){
        // Arrange
        AlbumRecommendation albumRecommendation =new AlbumRecommendation(1, 1 , true);
        AlbumRecommendation expectedAlbumRecommendation =new AlbumRecommendation(1, 1, true);
        repo.save(albumRecommendation);
        expectedAlbumRecommendation.setId(albumRecommendation.getId());
        assertEquals(expectedAlbumRecommendation, albumRecommendation);

        // Act
        List<AlbumRecommendation> allTheAlbumRecommendation = repo.findAll();

        // Assert
        assertEquals(1, allTheAlbumRecommendation.size());

        // Act
        repo.deleteById(albumRecommendation.getId());

        allTheAlbumRecommendation = repo.findAll();
        assertEquals(0, allTheAlbumRecommendation.size());
    }


}