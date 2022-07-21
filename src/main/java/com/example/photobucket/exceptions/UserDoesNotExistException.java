package com.example.photobucket.exceptions;

/**
 * The UserDoesNotExist exception is a runtime exception to be thrown
 * when a requested user does not exist
 */
public class UserDoesNotExistException extends RuntimeException {

    /**
     * Creates a new UserDoesNotExistException
     */
    public UserDoesNotExistException() {
        super();
    }

    /**
     * Creates a new UserDoesNotExistException with the given message
     *
     * @param message the message that will be displayed when the
     *                UserDoesNotExistException is thrown
     */
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
