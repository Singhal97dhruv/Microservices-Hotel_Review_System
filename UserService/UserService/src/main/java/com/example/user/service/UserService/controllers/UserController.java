package com.example.user.service.UserService.controllers;

import com.example.user.service.UserService.entities.User;
import com.example.user.service.UserService.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//@Slf4j
/**
 * REST controller that handles user-related API endpoints.
 * Includes Resilience4j annotations for rate limiting, retry, and circuit breaking.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * Creates a new user and assigns a random UUID as the user ID.
     *
     * @param user User data from the request body.
     * @return Created user with assigned ID and persisted data.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        // Generate a random UUID for user ID
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        // Save user to database
        User savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

    }
    // Counter to track retry attempts (for testing retry behavior)
    int retryCount=1;

    /**
     * Retrieves a single user by ID.
     * Uses RateLimiter to prevent abuse and fallback method for resilience.
     *
     * Uncomment one annotation at a time if you want to test Retry or CircuitBreaker.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user with ratings and hotel info, or fallback user if services fail.
     */
    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
//    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback" )
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        log.info("Retry Count: {}",retryCount);
        retryCount++;

        // Delegate user fetching to service layer
        User user=userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    /**
     * Fallback method called when service is unavailable or circuit breaker is open.
     *
     * @param userId ID of the user requested.
     * @param ex     The exception thrown (can be logged or inspected).
     * @return Dummy user response with fallback message.
     */
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
        log.info("Fallback is executed because service is down",ex.getMessage());
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created because some service is down")
                .userId("12345")
                .build();
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(user);
    }
    /**
     * Retrieves all users.
     *
     * @return List of all users from the database.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users=userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


}
