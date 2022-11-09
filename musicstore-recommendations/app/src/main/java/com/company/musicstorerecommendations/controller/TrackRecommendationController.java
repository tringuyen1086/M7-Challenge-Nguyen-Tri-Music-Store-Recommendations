package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trackRecommendation")
public class TrackRecommendationController {

    @Autowired
    private TrackRecommendationRepository trackRecommendationRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendation createTrackRecommendation(@RequestBody @Valid TrackRecommendation trackRecommendation){
        return trackRecommendationRepository.save(trackRecommendation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TrackRecommendation> getAllTrackRecommendations(){
        return trackRecommendationRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackRecommendation getTrackRecommendationById(@PathVariable int id){
        Optional<TrackRecommendation> optionalTrackRecommendation= trackRecommendationRepository.findById(id);// what happen if we dont use optional; null has many reason like none, error
        if (optionalTrackRecommendation.isPresent() == false){
            throw new RuntimeException("No TrackRecommendation found with the id " + id);
        }
        return optionalTrackRecommendation.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackRecommendation(@PathVariable int id, @RequestBody TrackRecommendation trackRecommendation){
        if (trackRecommendation.getId() == null){
            trackRecommendation.setId(id);
        } else if (trackRecommendation.getId() != id) {
            throw new BadIdException("The id (" + id + ") in the path is " +
                    "not matching with the id (" + trackRecommendation.getId() + ") in the body.");
        }
        trackRecommendationRepository.save(trackRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackRecommendation(@PathVariable("id") int id) {
        trackRecommendationRepository.deleteById(id);
    }

}
