package com.example.user.service.UserService.exceptions;

import com.example.user.service.UserService.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for your application.
 * Catches specific exceptions and returns a structured response.
 */
@RestControllerAdvice  // Ensures this class handles exceptions across all controllers
public class GlobalExceptionHandler {
    /**
     * Handles the case where a requested resource is not found.
     * This method intercepts ResourceNotFoundException and returns a custom response.
     *
     * @param ex The ResourceNotFoundException thrown by the controller/service layer.
     * @return A ResponseEntity containing ApiResponse with error details.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ex){

        // Extract message from the exception
    String message=ex.getMessage();

        // Build the API response with error info
    ApiResponse apiResponse= ApiResponse.builder().message(message).success(true).status(HttpStatus.NOT_FOUND).build();

    return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);

    }

}
