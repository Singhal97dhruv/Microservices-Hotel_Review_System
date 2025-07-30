package com.example.user.service.UserService.services.impl;

import com.example.user.service.UserService.entities.Hotel;
import com.example.user.service.UserService.entities.Rating;
import com.example.user.service.UserService.entities.User;
import com.example.user.service.UserService.exceptions.ResourceNotFoundException;
import com.example.user.service.UserService.external.services.HotelService;
import com.example.user.service.UserService.repositories.UserRepository;
import com.example.user.service.UserService.services.UserService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the UserService interface.
 * Handles user creation, fetching all users, and fetching a single user along with their ratings and hotel details.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private
    HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    /**
     * Saves a new user to the database.
     *
     * @param user The user to be saved.
     * @return The saved user entity.
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


    /**
     * Retrieves all users from the database.
     *
     * @return List of all users.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Fetches a user by ID, along with their ratings and hotel information for each rating.
     * The result is cached to avoid repeated external calls.
     *
     * @param userId ID of the user to retrieve.
     * @return The user entity with ratings and hotel data.
     * @throws ResourceNotFoundException if the user is not found.
     */
    @Override
//    @Cacheable(value = "users",key = "#userId")    ===================== stopped caching for testing ========================
    public User getUser(String userId) {
        // Fetch user from database or throw exception if not found
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with givenID not found on server!! : " + userId));
        // Call RATING-SERVICE to fetch ratings for the user
        Rating[] ratingsOfUsers = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);

        logger.info("{}", ratingsOfUsers);

        List<Rating> ratings = Arrays.stream(ratingsOfUsers).toList();


// For each rating, fetch corresponding hotel using HotelService (FeignClient)
        List<Rating> ratingList = ratings.stream().map(rating -> {

//          ResponseEntity<Hotel> hotelResponseEntity= restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//            Hotel hotel= hotelResponseEntity.getBody();
//            logger.info("response status code: {}",hotelResponseEntity.getStatusCode());

            // Fetch hotel from HOTEL-SERVICE and set it in the rating
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            rating.setHotel(hotel);
            return rating;

        }).collect(Collectors.toList());

        // Attach enriched ratings to user object
        user.setRatings(ratingList);

        return user;
    }
}
