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

	@Autowired
	private IUserService userService;
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value = "id/{id}", method = RequestMethod.GET)
	public @ResponseBody User retrieveUserById(@PathVariable Long id)
			throws DecisionMakerException, EntityDoesNotExistException {
		return userService.retrieveUserById(id);
	}
	
	@RequestMapping(value = "username/{username:.+}", method = RequestMethod.GET)
	public @ResponseBody User retrieveUserByUsername(@PathVariable String username)
			throws DecisionMakerException {
		return userService.retrieveUserByUsername(username);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String createUser(@RequestBody User user) throws DecisionMakerException {
		userService.saveUser(user);
		return "The user '" + user.getAccount().getUsername() + "' has been created."; 
	}
	
}
