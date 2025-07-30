package com.example.user.service.UserService.exceptions;

/**
 * Custom exception thrown when a requested resource (like a user or rating) is not found.
 * This extends RuntimeException so it can be thrown without being explicitly caught.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Default constructor with a generic message.
     */
    public ResourceNotFoundException() {
        super("Resource not found!!");
    }
    /**
     * Constructor that accepts a custom error message.
     *
     * @param message Custom error message to be used when exception is thrown.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
