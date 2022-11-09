package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.LabelRecommendation;
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
public class LabelRecommendationRepositoryTest {

    @Autowired
    private LabelRecommendationRepository repo;
    @Before
    public void setUp()throws Exception{
        repo.deleteAll();

    }
    @Test
    public void shouldInteractWithDatabase(){
        LabelRecommendation labelRecommendation =new LabelRecommendation(1, 1, true);
        LabelRecommendation expectedLabelRecommendation =new LabelRecommendation(1, 1, true);
        repo.save(labelRecommendation);
        expectedLabelRecommendation.setId(labelRecommendation.getId());
        assertEquals(expectedLabelRecommendation, labelRecommendation);

        // Act
        List<LabelRecommendation> allTheLabelRecommendation = repo.findAll();

        // Assert
        assertEquals(1, allTheLabelRecommendation.size());

        // Act
        repo.deleteById(labelRecommendation.getId());

        allTheLabelRecommendation = repo.findAll();
        assertEquals(0, allTheLabelRecommendation.size());
    }

}