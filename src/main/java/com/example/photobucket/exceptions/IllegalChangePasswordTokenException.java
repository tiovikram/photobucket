package com.example.photobucket.exceptions;

/**
 * The IllegalChangePasswordTokenException is a runtime exception to be
 * thrown when a provided "change-password" token is not in the correct
 * 6-digit format
 */
public class IllegalChangePasswordTokenException extends RuntimeException {

    /**
     * Creates a new IllegalChangePasswordTokenException
     */
    public IllegalChangePasswordTokenException() {
        super();
    }

    /**
     * Creates a new IllegalChangePasswordTokenException with the given message
     *
     * @param message the message that will be displayed when the
     *                IllegalChangePasswordTokenException is thrown
     */
    public IllegalChangePasswordTokenException(String message) {
        super(message);
    }
}
