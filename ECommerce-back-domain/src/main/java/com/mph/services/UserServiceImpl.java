package com.mph.services;

import java.util.List;
import java.util.ArrayList;

import java.util.Date;

import java.util.Optional;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.UserService;
import com.mph.services.interfaces.RoleService;

import com.mph.services.exceptions.InvalidUsernameFormatException;
import com.mph.services.exceptions.UsernameAlreadyInUseException;
import com.mph.services.exceptions.InvalidEmailFormatException;
import com.mph.services.exceptions.EmailAlreadyInUseException;
import com.mph.services.exceptions.InvalidPasswordConfirmationException;
import com.mph.services.exceptions.InvalidPhoneFormatException;
import com.mph.services.exceptions.PhoneAlreadyInUseException;
import com.mph.services.exceptions.UserNotFoundException;

import com.mph.repositories.interfaces.UserRepository;

import com.mph.entities.User;
import com.mph.entities.Role;

@Service
public class UserServiceImpl implements UserService {

	private static final String EMAIL_REGEX = "^(?=.{1,64}@)[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*@[^-][a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,})$";
	private static final String PHONE_REGEX = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";

	@Autowired
	public RoleService roleService;

	@Autowired
	public UserRepository userRepository;

	@Override
	public void checkUser(String username, String email, String password, String confirmPassword) {

		checkUsername(username);
		checkEmail(email);
		checkPassword(password, confirmPassword);

	}

	@Override
	public User addUser(String username, String email, String password) {

		List<Role> roles = new ArrayList<Role>();

		roles.add(roleService.getRoleById(1));

		User user = User.of(username, email, password, new Date(), new Date(), roles);

		user = userRepository.save(user);

		return user;

	}

	@Override
	public User updateUserFirstname(String username, String firstname) {

		User user = getUserByUsername(username);

		user.setFirstname(firstname);

		user = userRepository.save(user);

		return user;

	}

	@Override
	public User updateUserLastname(String username, String lastname) {

		User user = getUserByUsername(username);

		user.setLastname(lastname);

		user = userRepository.save(user);

		return user;

	}

	@Override
	public User updateUserEmail(String username, String email) {

		checkEmail(email);

		User user = getUserByUsername(username);

		user.setEmail(email);

		user = userRepository.save(user);

		return user;

	}

	@Override
	public User updateUserPhone(String username, String phone) {

		checkPhone(phone);

		User user = getUserByUsername(username);

		user.setPhone(phone);

		user = userRepository.save(user);

		return user;

	}

	@Override
	public User updateUserPassword(String username, String password, String confirmPassword, String encodedPassword) {

		checkPassword(password, confirmPassword);

		User user = getUserByUsername(username);

		user.setPassword(encodedPassword);

		user = userRepository.save(user);

		return user;

	}

	@Override
	public User updateUserLastConnection(String username, Date lastConnection) {

		User user = getUserByUsername(username);

		user.setLastConnection(lastConnection);

		user = userRepository.save(user);

		return user;

	}

	@Override
	public User addUserRole(String username, String roleName) {

		User user = getUserByUsername(username);
		Role role = roleService.getRoleByName(roleName);

		user.addRole(role);

		user = userRepository.save(user);

		return user;

	}

	@Override
	public User removeUserRole(String username, String roleName) {

		User user = getUserByUsername(username);
		Role role = roleService.getRoleByName(roleName);

		user.removeRole(role);

		user = userRepository.save(user);

		return user;

	}

	@Override
	public User getUserByUsername(String username) {

		Optional<User> optionalUser = userRepository.findByUsername(username);

		if(optionalUser.isEmpty())
			throw new UserNotFoundException();

		User user = optionalUser.get();

		return user;

	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	private void checkUsername(String username) {

		if(username.contains(" "))
			throw new InvalidUsernameFormatException();

		if(isUsernameAlreadyInUse(username))
			throw new UsernameAlreadyInUseException();

	}

	private void checkEmail(String email) {

		if(!isEmail(email))
			throw new InvalidEmailFormatException();

		if(isEmailAlreadyInUse(email))
			throw new EmailAlreadyInUseException();

	}

	private void checkPhone(String phone) {

		if(!isPhone(phone))
			throw new InvalidPhoneFormatException();

		if(isPhoneAlreadyInUse(phone))
			throw new PhoneAlreadyInUseException();

	}

	private void checkPassword(String password, String confirmPassword) {

		if(!password.equals(confirmPassword))
			throw new InvalidPasswordConfirmationException();

	}

	private boolean isUsernameAlreadyInUse(String username) {

		Optional<User> user = userRepository.findByUsername(username);

		return user.isPresent();

	}

	private boolean isEmailAlreadyInUse(String email) {

		Optional<User> user = userRepository.findByEmail(email);

		return user.isPresent();

	}

	private boolean isPhoneAlreadyInUse(String phone) {

		Optional<User> user = userRepository.findByPhone(phone);;

		return user.isPresent();

	}

	private boolean isEmail(String email) {
		return Pattern.matches(EMAIL_REGEX, email);
	}

	private boolean isPhone(String phone) {
		return Pattern.matches(PHONE_REGEX, phone);
	}

}