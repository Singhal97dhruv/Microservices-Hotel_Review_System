package com.lcwd.rating.Rating.Service.services;

import com.lcwd.rating.Rating.Service.entities.Rating;

import java.util.List;

public interface RatingService {

    Rating create(Rating rating);


    //Get all ratings
    List<Rating>getRatings();

    //Get all ratings by userId
    List<Rating>getRatingByUserId(String userId);
    //Get all ratings by HotelId
    List<Rating>getRatingByHotelId(String hotelId);
}
