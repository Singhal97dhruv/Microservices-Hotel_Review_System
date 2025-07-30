package com.lcwd.rating.Rating.Service.repository;

import com.lcwd.rating.Rating.Service.entities.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating,String> {

    //Custom finders
    List<Rating> findByUserId(String userId);
    List<Rating> findByHotelId(String hotelId);
}
