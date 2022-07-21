package com.example.photobucket.services.loginService;

import com.example.photobucket.exceptions.IllegalChangePasswordTokenException;
import com.example.photobucket.exceptions.UserDoesNotExistException;

import java.util.HashMap;
import java.util.Map;

/**
 * The Login Service used for the Photobucket Application
 */
public class LoginService {

    // The existing users of the Login Service.
    private Map<String, User> users;

    /**
     * Initializes the Login Service
     */
    public LoginService() {
        users = new HashMap<>();
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
        return requestedUser.logIn(password);
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
        User requestedUser = users.get(username);
        return requestedUser.loggedIn();
    }

    /**
     * Logs out an existing user of the Login Service
     *
     * @param username the username of a Login Service user
     * @throws UserDoesNotExistException if the username does not belong to an existing user
     */
    public void logoutUser(String username) {
        if (!userExists(username)) throw new UserDoesNotExistException("User Does Not Exist");
        User requestedUser = users.get(username);
        requestedUser.logOut();
    }

    /**
     * Generates a "reset-password" token for a user of the Login Service
     *
     * @param username the username of a Login Service user
     * @throws UserDoesNotExistException if the username does not belong to an existing user
     */
    public void generateToken(String username) {
        if (!userExists(username)) throw new UserDoesNotExistException("User Does Not Exist");
        User requestedUser = users.get(username);
        requestedUser.generateToken();
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
        return requestedUser.changePassword(password, token);
    }

    /**
     * A User of the Login Service
     */
    private static class User {

        // The username of the user
        public String username;

        // The password of the user
        public String password;

        // The log-in state of the user
        public boolean loggedIn;

        // The "reset-password" token
        public String token;

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
            if (!this.password.equals(password)) return false;
            loggedIn = true;
            return true;
        }

        /**
         * Logs out the User
         */
        public void logOut() {
            loggedIn = false;
        }

        /**
         * Returns whether the user is logged-in
         *
         * @return true if the user is logged-in, otherwise false
         */
        public boolean loggedIn() {
            return loggedIn;
        }

        /**
         * Generates a new "reset password" token for the user
         */
        public void generateToken() {
            StringBuilder token = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                token.append((int) (Math.random() * 10));
            }
            this.token = token.toString();
        }

        /**
         * Attempts to change the user's password. The password will be
         * changed if the given token matches the "reset-password" token
         * assigned to the user.
         *
         * @param token the "reset-password" token to be used in the change-password attempt
         * @param password the String the user's password will be changed to if the
         *                 change-password attempt is successful
         * @return true if the password change was successful, otherwise false
         */
        public boolean changePassword(String token, String password) {
            // Reject non-matching tokens and empty string tokens. I'm treating
            // empty string tokens as the default token state, when the user
            // hasn't requested to change their password. Valid tokens are
            // reset to the empty string after a successful password change
            if (!this.token.equals(token) || this.token.equals("")) return false;
            this.password = password;
            this.token = "";
            return true;
        }
    }
}
