package com.example.photobucket.services.loginService;

import static com.example.photobucket.Exceptions.IllegalChangePasswordTokenException;
import static com.example.photobucket.Exceptions.UserDoesNotExistException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The Login Service used for the Photobucket Application
 */
public class LoginService {

    // The existing users of the Login Service.
    private Map<String, User> users;

    // The set of logged-in users
    private Set<String> loggedInUsers;

    // The "reset-password
    private Map<String, String> resetPasswordTokens;

    /**
     * Initializes the Login Service
     */
    public LoginService() {
        users = new HashMap<>();
        loggedInUsers = new HashSet<>();
        resetPasswordTokens = new HashMap<>();
    }

    /**
     * Creates a new user account if an account with the
     * same username does not already exist in the Login Service
     *
     * @param username the username of the new user
     * @param password the passsword of the new user
     * @return true if a new account was created, otherwise false
     */
    public boolean createAccount(String username, String password) {
        if (userExists(username)) return false;
        User newUser = new User(username, password);
        users.put(username, newUser);
        resetPasswordTokens.put(username, "");
        return true;
    }

    /**
     * Attempts to log in a user account with the given username and password
     *
     * @param username the username to be used in the login attempt
     * @param password the password to be used in the login attempt
     * @return true if the login was successful, otherwise false
     */
    public boolean login(String username, String password) {
        if (!userExists(username)) return false;
        User requestedUser = users.get(username);
        if(requestedUser.logIn(password)) {
            loggedInUsers.add(username);
            return true;
        }
        return false;
    }

    /**
     * Returns whether a user with the given username exists in the Login Service
     *
     * @param username the username to be searched for in the Login Service
     * @return true if the user exists, otherwise false
     */
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    /**
     * Returns whether an existing user is logged in to the Login Service
     *
     * @param username the username of a Login Service user
     * @throws UserDoesNotExistException if the username does not belong to an existing user
     * @return true if the user is logged in, otherwise false
     */
    public boolean userLoggedIn(String username) {
        if (!userExists(username)) throw new UserDoesNotExistException("User Does Not Exist");
        return loggedInUsers.contains(username);
    }

    /**
     * Logs out an existing user of the Login Service
     *
     * @param username the username of a Login Service user
     * @throws UserDoesNotExistException if the username does not belong to an existing user
     */
    public void logoutUser(String username) {
        if (!userExists(username)) throw new UserDoesNotExistException("User Does Not Exist");
        loggedInUsers.remove(username);
    }

    /**
     * Generate a new token for an existing user of the Login Service
     *
     * @param username the username of a Login Service user
     * @throws UserDoesNotExistException if the username does not belong to an existing user
     */
    public void generateToken(String username) {
        if (!userExists(username)) throw new UserDoesNotExistException("User Does Not Exist");
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            token.append((int) (Math.random() * 10));
        }
        resetPasswordTokens.put(username, token.toString());
    }

    /**
     * Attempts to change the password for a user of the Login Service
     *
     * @param username the username of an existing user of the Login Service
     * @param password the password of an existing user of the Login Service
     * @param token the "reset-password" token to be used in the password change attempt
     * @throws UserDoesNotExistException if the username does not belong to an existing user
     * @throws IllegalChangePasswordTokenException if the "reset-password" token is invalid
     *                                             (doesn't consist of 6 digits)
     * @return true is the password change was successful, otherwise false
     */
    public boolean changePassword(String username, String token, String password) {
        if (!userExists(username)) {
            throw new UserDoesNotExistException("User Does Not Exist");
        } else if (!token.matches("\\d{6}")) {
            throw new IllegalChangePasswordTokenException("'reset-password' token needs to consist of 6 digits");
        }
        User requestedUser = users.get(username);
        String officialToken = resetPasswordTokens.get(username);
        if (requestedUser.changePassword(officialToken, token, password)) {
            resetPasswordTokens.put(username, "");
            return true;
        }
        return false;
    }

    /**
     * A User of the Login Service
     */
    private static class User {

        // The username of the user
        private String username;

        // The password of the user
        private String password;

        /**
         * Creates a new user with the given username and password
         *
         * @param username the username of the user
         * @param password the password of the user
         */
        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        /**
         * Attempts to log the user in with the given password
         *
         * @param password the password to attempt with log-in with
         * @return true is the log-in attempt was successful, otherwise false
         */
        public boolean logIn(String password) {
            return this.password.equals(password);
        }

        /**
         * Attempts to change the user's password. The password will be
         * changed if the given token matches the "reset-password" token
         * assigned to the user.
         *
         * @param officialToken the user's actual "rest-password" token
         * @param testToken the "reset-password" token to be used in the change-password attempt
         * @param password the String the user's password will be changed to if the
         *                 change-password attempt is successful
         * @return true if the password change was successful, otherwise false
         */
        public boolean changePassword(String officialToken, String testToken, String password) {
            // Empty String is the default state of "reset-password" tokens, used
            // when the user hasn't requested a password change.
            if (!officialToken.equals(testToken) || officialToken.equals("")) return false;
            this.password = password;
            return true;
        }
    }
}
