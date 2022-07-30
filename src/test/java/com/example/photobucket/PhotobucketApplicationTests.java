package com.example.photobucket;

import com.example.photobucket.services.loginService.LoginService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for the PhotobucketApplication
 */
@SpringBootTest
class PhotobucketApplicationTests {

	private LoginService loginService;

	/**
	 * Tests if the application is able to load the Spring context
	 */
	@Test
	void contextLoads() {
	}
}
