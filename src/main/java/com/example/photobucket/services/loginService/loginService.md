# Login Service Specification
  
## Description
A stateful service (although the convention followed across the industry is for services to be stateless, but we can iterate upon that) that allows a user to perform basic user management actions such as account creation, login, checking if user exists, checking if user is logged in, returning logged in users, logging out and changing password.   
  
## Package
The service must belong to package `com.example.photobucket.services.loginService`  
  
## Methods
- `LoginService()`: Since the service is stateful, the service must have a constructor with which it is initialised upon startup of the application.
- `bool createAccount(String username, String password)`: Creates a new user with specified username and password. Must not overwrite an existing user account. Returns whether the account creation was possible.
- `bool login(String username, String password)`: Performs login for user with specified username and password. Returns if login was successful. Must keep track of any users currently logged into the system.
- `bool userExists(String username)`: Returns whether user already exists.
- `bool userLoggedIn(String username)`: Returns whether the user specified with username is currently logged in. 
- `void logoutUser(String username)`: If user is already logged in, log user out, else throw an exception.
- `void generateToken(String username)`: Generate a verification token for the user to reset password (example: can be a randomly generate six digit number).
- `bool changePassword(String username, String token)`: Change the password of an existing user if a change password token has been requested and the token provided by the user matches the generated token.
  
## Exceptions
All methods above must throw exceptions as reasonable. It is upto developer discretion to create Exceptions for invalid operations. Exceptions must be placed in package `com.example.photobucket.exceptions`.
