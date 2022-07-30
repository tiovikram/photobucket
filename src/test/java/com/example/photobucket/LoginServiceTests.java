package com.example.photobucket.services.loginService;

import com.example.photobucket.Exceptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the LoginService class
 */
class LoginServiceTests {

	private LoginService loginService;

	/**
	 * Tests initiating a new LoginService instance
	 */
	@Test
	public void testCreateNewLoginService() {
		loginService = new LoginService();
	}

	/**
	 * Tests successful creation of a new user (user does not already exist)
	 */
	@Test
	public void testSuccessfulCreateNewAccount() {
		loginService = new LoginService();
		assertTrue(loginService.createAccount("username", "password"));
	}

	/**
	 * Tests unsuccessful creation of a new user (user already exists)
	 */
	@Test
	public void testUnsuccessfulCreateNewAccount() {
		loginService = new LoginService();
		loginService.createAccount("username", "password");
		assertFalse(loginService.createAccount("username", "password"));
	}

	/**
	 * Tests successful login attempt (username exists and password matches the
	 * password for that user)
	 */
	@Test
	public void testSuccessfulLogin() {
		loginService = new LoginService();
		loginService.createAccount("username", "password");
		assertTrue(loginService.login("username", "password"));
	}

	/**
	 * Tests unsuccessful login attempt (username exists and password does not match
	 * the password for that user)
	 */
	@Test
	public void testUnsuccessfulLoginIncorrectPassword() {
		loginService = new LoginService();
		loginService.createAccount("username", "password");
		assertFalse(loginService.login("username", ""));
	}

	/**
	 * Tests a UserDoesNotExist exception is thrown when attempting
	 * to log in to an account that does not exist
	 */
	@Test
	public void testUnsuccessfulLoginUserDoesNotExist() {
		loginService = new LoginService();
		assertFalse(loginService.login("username", "password"));
	}

	/**
	 * Tests userExists() returns true when the user exists
	 */
	@Test
	public void testUserExistCorrectReturnsTrue() {
		loginService = new LoginService();
		loginService.createAccount("username", "password");
		assertTrue(loginService.userExists("username"));
	}

	/**
	 * Tests userExists() returns false when the user does not exist
	 */
	@Test
	public void testUserExistsCorrectReturnsFalse() {
		loginService = new LoginService();
		assertFalse(loginService.userExists("username"));
	}

	/**
	 * Tests a UserDoesNotExist exception is thrown when checking
	 * the logged-in status of a user that does not exist
	 */
	@Test
	public void testUserLoggedInExceptionsCorrect() {
		loginService = new LoginService();
		assertThrows(Exceptions.UserDoesNotExistException.class, () -> loginService.userLoggedIn("username"));
	}

	/**
	 * Tests userLoggedIn() returns true when the user is logged-in
	 */
	@Test
	public void testUserLoggedInCorrectReturnsTrue() {
		loginService = new LoginService();
		loginService.createAccount("username", "password");
		assertTrue(loginService.login("username", "password"));
	}

	/**
     * Tests userLoggedIn() returns false when the user is not logged-in
	 */
	@Test
	public void testUserLoggedInCorrectReturnsFalse() {
		loginService = new LoginService();
		loginService.createAccount("username", "password");
		assertFalse(loginService.userLoggedIn("username"));
	}

	/**
     * Tests a UserDoesNotExist exception is thrown when logging out
	 * a user that does not exist
	 */
	@Test
	public void testLogOutUserExceptionsCorrect() {
		loginService = new LoginService();
		assertThrows(Exceptions.UserDoesNotExistException.class, () -> loginService.logoutUser("username"));
	}

	/**
     * Tests logOut() successfully logs-out an existing user
	 */
	@Test
	public void testLogOutUserCorrect() {
		loginService = new LoginService();
		loginService.createAccount("username", "password");
		loginService.login("username", "password");
		loginService.logoutUser("username");
		assertFalse(loginService.userLoggedIn("username"));
	}

	/**
     * Tests an UserDoesNotExist exception is thrown when changing the
	 * password of a user that does not exist
	 */
	@Test
	public void testChangePasswordThrowsUserDoesNotExistException() {
		loginService = new LoginService();
		assertThrows(Exceptions.UserDoesNotExistException.class, () -> {
			loginService.changePassword("username", "000000", "password");
		});
	}

	/**
     * Tests an IllegalChangePasswordToken is thrown when changing the
	 * password with an incorrect (non-6-digit) token
	 */
	@Test
	public void testChangePasswordThrowsIllegalChangePasswordTokenException() {
		loginService = new LoginService();
		loginService.createAccount("username", "password");
		assertThrows(Exceptions.UserDoesNotExistException.class, () -> {
			loginService.changePassword("username", "", "password");
		});
		assertThrows(Exceptions.UserDoesNotExistException.class, () -> {
			loginService.changePassword("username", "00000", "password");
		});
		assertThrows(Exceptions.UserDoesNotExistException.class, () -> {
			loginService.changePassword("username", "aaaaaa", "password");
		});
	}

	/**
	 * Tests changePassword() won't change a user's password if the user
	 * hasn't generated a change-password token
	 */
	@Test
	public void testCantChangePasswordWithNoGeneratedToken() {
		loginService = new LoginService();
		loginService.createAccount("username", "password");
		loginService.changePassword("username", "000000", "new password");
		assertTrue(loginService.login("username", "password"));
	}

	// Tests a properly formatted token is generated when
	// generateToken() is called (Can't test in current state of LoginService;
	// we can't currently access a user's change-password token)
	@Test
	@Disabled
	public void testGenerateToken() {

	}

	// Tests changePassword() correctly changes a user's password when
	// correct change-password token is provided (Can't test in current state of LoginService;
	// we can't currently access a user's change-password token)
	@Test
	@Disabled
	public void testSuccessfulChangePasswordCorrect() {

	}

	// Tests changePassword() will not change a user's password when
	// an incorrect change-password token is provided (Can't test in current state of LoginService;
	// we can't currently access a user's change-password token)
	@Test
	@Disabled
	public void testUnsuccessfulChangePasswordCorrect() {

	}
}
