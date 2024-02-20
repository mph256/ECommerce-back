package com.mph.services.interfaces;

import java.util.List;

import java.util.Date;

import com.mph.services.exceptions.InvalidUsernameFormatException;
import com.mph.services.exceptions.UsernameAlreadyInUseException;
import com.mph.services.exceptions.InvalidEmailFormatException;
import com.mph.services.exceptions.EmailAlreadyInUseException;
import com.mph.services.exceptions.InvalidPasswordConfirmationException;
import com.mph.services.exceptions.InvalidPhoneFormatException;
import com.mph.services.exceptions.PhoneAlreadyInUseException;
import com.mph.services.exceptions.RoleNotFoundException;
import com.mph.services.exceptions.UserNotFoundException;

import com.mph.entities.User;

public interface UserService {

	/**
	 * Checks the validity of the username, email and password entered during a registration attempt.
	 * 
	 * @param username the username entered
	 * @param email the email entered
	 * @param password the password entered
	 * @param confirmPassword the password confirmation entered
	 * 
	 * @throws InvalidUsernameFormatException if the format of the entered username is invalid
	 * @throws UsernameAlreadyInUseException if the entered username is already in use
	 * @throws InvalidEmailFormatException if the format of the entered email is invalid
	 * @throws EmailAlreadyInUseException if the entered email is already in use
	 * @throws InvalidPasswordConfirmationException if the entered passwords do not match
	 */
	public void checkUser(String username, String email, String password, String confirmPassword) throws InvalidUsernameFormatException, UsernameAlreadyInUseException,
		InvalidEmailFormatException, EmailAlreadyInUseException, InvalidPasswordConfirmationException;

	/**
	 * Creates a new user.
	 * 
	 * <br>
	 * Argument validity checks must be performed before this method is called.
	 * 
	 * @param username the username entered
	 * @param email the email entered
	 * @param password the password entered
	 * 
	 * @return the new user created
	 */
	public User addUser(String username, String email, String password);

	/**
	 * Updates the first name of a user.
	 * 
	 * @param username the username of the user to update
	 * @param firstname the new first name
	 * 
	 * @return the updated user
	 * 
	 * @throws UserNotFoundException if no user with this username is found
	 */
	public User updateUserFirstname(String username, String firstname) throws UserNotFoundException;

	/**
	 * Updates the last name of a user.
	 * 
	 * @param username the username of the user to update
	 * @param lastname the new last name
	 * 
	 * @return the updated user
	 * 
	 * @throws UserNotFoundException if no user with this username is found
	 */
	public User updateUserLastname(String username, String lastname) throws UserNotFoundException;

	/**
	 * Updates the email of a user.
	 * 
	 * @param username the username of the user to update
	 * @param email the new email
	 * 
	 * @return the updated user
 	 * 
 	 * @throws InvalidEmailFormatException if the email format is invalid
 	 * @throws EmailAlreadyInUseException if the email is already in use
	 * @throws UserNotFoundException if no user with this username is found
	 */
	public User updateUserEmail(String username, String email) throws InvalidEmailFormatException, EmailAlreadyInUseException,
		UserNotFoundException;

	/**
	 * Updates the phone of a user.
	 * 
	 * @param username the username of the user to update
	 * @param phone the new phone
	 * 
	 * @return the updated user
 	 * 
 	 * @throws InvalidPhoneFormatException if the phone format is invalid
 	 * @throws PhoneAlreadyInUseException if the phone is already in use
	 * @throws UserNotFoundException if no user with this username is found
	 */
	public User updateUserPhone(String username, String phone) throws InvalidPhoneFormatException, PhoneAlreadyInUseException, 
		UserNotFoundException;

	/**
	 * Updates the password of a user.
	 * 
	 * @param username the username of the user to update
	 * @param password the new password
	 * @param confirmPassword the password confirmation
	 * @param encodedPassword the encoded password
	 * 
	 * @return the updated user
 	 * 
 	 * @throws InvalidPasswordConfirmationException if the passwords do not match
	 * @throws UserNotFoundException if no user with this username is found
	 */
	public User updateUserPassword(String username, String password, String confirmPassword, String encodedPassword) throws InvalidPasswordConfirmationException, UserNotFoundException;

	/**
	 * Updates the last connection date of a user.
	 * 
	 * @param username the username of the user to update
	 * @param lastConnection the new last connection date
	 * 
	 * @return the updated user
 	 * 
	 * @throws UserNotFoundException if no user with this username is found
	 */
	public User updateUserLastConnection(String username, Date lastConnection) throws UserNotFoundException;

	/**
	 * Adds a role to a user.
	 * 
	 * @param username the username of the user to update
	 * @param roleName the name of the role to add
	 * 
	 * @return the updated user
 	 * 
	 * @throws UserNotFoundException if no user with this username is found
	 * @throws RoleNotFoundException if no role with this name is found
	 */
	public User addUserRole(String username, String roleName) throws UserNotFoundException, RoleNotFoundException;

	/**
	 * Removes a role to a user.
	 * 
	 * @param username the username of the user to update
	 * @param lastname the name of the role to remove
	 * 
	 * @return the updated user
 	 * 
	 * @throws UserNotFoundException if no user with this username is found
	 * @throws RoleNotFoundException if no role with this name is found
	 */
	public User removeUserRole(String username, String roleName) throws UserNotFoundException, RoleNotFoundException;

	public User getUserByUsername(String username) throws UserNotFoundException;

	public List<User> getUsers();

}