package com.decision.maker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.decision.maker.domain.user.User;
import com.decision.maker.exception.DecisionMakerException;
import com.decision.maker.exception.EntityDoesNotExistException;
import com.decision.maker.service.user.IUserService;

@Controller
@RequestMapping(value = "user")
public class UserController {

	@Autowired private IUserService userService;

	@RequestMapping(value = "count", method = RequestMethod.GET)
	public @ResponseBody String retrieveCount() {
		return "There are " + userService.retrieveCount() + " Users in " + userService.getTargetDatabase();
	}
	
	@RequestMapping(value = "id/{id}", method = RequestMethod.GET)
	public @ResponseBody User retrieveUserById(@PathVariable Long id)
			throws DecisionMakerException, EntityDoesNotExistException {
		return userService.retrieveUserById(id);
	}
	
	@RequestMapping(value = "barebone/{id}", method = RequestMethod.GET)
	public @ResponseBody User retrieveBareboneUserById(@PathVariable Long id) throws EntityDoesNotExistException {
		return userService.retrieveBareboneUserById(id);
	}
	
	@RequestMapping(value = "username/{username:.+}", method = RequestMethod.GET)
	public @ResponseBody User retrieveUserByUsername(@PathVariable String username)
			throws EntityDoesNotExistException {
		return userService.retrieveUserByUsername(username);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String createUser(@RequestBody User user)
			throws DecisionMakerException {
		userService.saveUser(user);
		return "The user '" + user.getAccount().getUsername() + "' has been created."; 
	}
	
	@RequestMapping(value = "id/{id}", method = RequestMethod.PUT)
	public @ResponseBody String updateUser(@PathVariable Long id, @RequestBody User user)
			throws DecisionMakerException, EntityDoesNotExistException {
		user.setId(id);
		userService.updateUser(user);
		return "The user " + id + ": '" + user.getAccount().getUsername() + "' has been updated.";
	}
	
	@RequestMapping(value = "id/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteUserById(@PathVariable Long id) throws EntityDoesNotExistException {
		userService.deleteUserById(id);
		return "User " + id + " has been deleted.";
	}
	
	@RequestMapping(value = "username/{username:.+}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteUserByUsername(@PathVariable String username) throws EntityDoesNotExistException {
		userService.deleteUserByUsername(username);
		return "The user '" + username + "' has been deleted.";
	}

	@RequestMapping(value = "id/{id}", method = RequestMethod.HEAD)
	public @ResponseBody String checkIfUserExistsById(@PathVariable Long id) {
		boolean exists = userService.checkIfUserExistsById(id);
		return "User " + id + ": " + generateExistString(exists);
	}
	
	@RequestMapping(value = "username/{username:.+}", method = RequestMethod.HEAD)
	public @ResponseBody String checkIfUserExistsByUsername(@PathVariable String username) {
		boolean exists = userService.checkIfUserExistsByUsername(username);
		return "User '" + username + "': " + generateExistString(exists);
	}
	
	private String generateExistString(boolean exists) {
		return (exists) ? "exists" : "does not exist";
	}
	
}