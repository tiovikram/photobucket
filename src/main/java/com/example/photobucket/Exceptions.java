package com.example.photobucket;

/**
 * Custom exceptions used in the Photobucket Application
 */
public class Exceptions {

    /**
     * The UserDoesNotExist exception is a runtime exception to be thrown
     * when a requested user does not exist
     */
    public static class UserDoesNotExistException extends RuntimeException {

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


    /**
     * The IllegalChangePasswordTokenException is a runtime exception to be
     * thrown when a provided "change-password" token is not in the correct
     * 6-digit format
     */
    public static class IllegalChangePasswordTokenException extends RuntimeException {

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
}
