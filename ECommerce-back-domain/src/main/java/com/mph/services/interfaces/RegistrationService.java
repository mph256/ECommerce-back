package com.mph.services.interfaces;

import com.mph.entities.User;

public interface RegistrationService {

	/**
	 * Registers a new user.
	 * 
	 * <br>
	 * This user will be initialized as a customer.
	 * 
	 * <br>
	 * Argument validity checks must be performed before this method is called.
	 * 
	 * @param the username entered
	 * @param the email entered
	 * @param the encoded password
	 * 
	 * @return the new registered user
	 */
	public User registerUser(String username, String email, String encodedPassword);

}