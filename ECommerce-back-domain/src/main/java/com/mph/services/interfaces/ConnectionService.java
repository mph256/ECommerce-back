package com.mph.services.interfaces;

import com.mph.entities.User;

public interface ConnectionService {

	/**
	 * Connects an existing user.
	 * 
	 * <br>
	 * Updates his last connection date.
	 * 
 	 * <br>
	 * Argument validity checks must be performed before this method is called.
	 * 
	 * @param username the username entered
	 * 
	 * @return the connected user
	 */
	public User connectUser(String username);

}