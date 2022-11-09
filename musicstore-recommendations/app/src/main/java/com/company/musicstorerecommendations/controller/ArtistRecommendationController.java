package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.repository.ArtistRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artistRecommendation")
public class ArtistRecommendationController {
    @Autowired
    private ArtistRecommendationRepository artistRecommendationRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendation createArtistRecommendation(@RequestBody @Valid ArtistRecommendation artistRecommendation){
        return artistRecommendationRepository.save(artistRecommendation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistRecommendation> getAllArtistRecommendations(){
        return artistRecommendationRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistRecommendation getArtistRecommendationById(@PathVariable int id){
        Optional<ArtistRecommendation> optionalArtistRecommendation= artistRecommendationRepository.findById(id);// what happen if we dont use optional; null has many reason like none, error
        if (optionalArtistRecommendation.isPresent() == false){
            throw new RuntimeException("No ArtistRecommendation found with the id " + id);
        }
        return optionalArtistRecommendation.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtistRecommendation(@PathVariable int id, @RequestBody ArtistRecommendation artistRecommendation){
        if (artistRecommendation.getId() == null){
            artistRecommendation.setId(id);
        } else if (artistRecommendation.getId() != id) {
            throw new BadIdException("The id (" + id + ") in the path is " +
                    "not matching with the id (" + artistRecommendation.getId() + ") in the body.");
        }
        artistRecommendationRepository.save(artistRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistRecommendation(@PathVariable("id") int id) {
        artistRecommendationRepository.deleteById(id);
    }

}
