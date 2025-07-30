package com.lcwd.rating.Rating.Service.controllers;

import com.lcwd.rating.Rating.Service.entities.Rating;
import com.lcwd.rating.Rating.Service.repository.RatingRepository;
import com.lcwd.rating.Rating.Service.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    /// Create Rating
    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating){
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
    }

    //Get all rating
    @GetMapping
    public ResponseEntity<List<Rating>>getRatings(){
//        return ResponseEntity.status(HttpStatus.FOUND).body(ratingService.getRatings());
        return ResponseEntity.ok(ratingService.getRatings());
    }

    //Get all rating
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>>getRatingByUserId(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.FOUND).body(ratingService.getRatingByUserId(userId));
    }

    //Get all rating
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>>getRatingByHotelId(@PathVariable String hotelId){
        return ResponseEntity.status(HttpStatus.FOUND).body(ratingService.getRatingByHotelId(hotelId));
    }

}

