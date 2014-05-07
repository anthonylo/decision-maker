package com.decisionmaker.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.decisionmaker.domain.user.Account;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.AccountValidationException;
import com.decisionmaker.exception.AlreadyLoggedInException;
import com.decisionmaker.exception.AlreadyLoggedOutException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.service.user.IUserService;
import com.decisionmaker.validator.user.AccountValidator;

@Controller
public class RootController {

	private Logger log = Logger.getLogger(RootController.class);
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "/{name:.+}")
	public @ResponseBody String printHelloWorld(@PathVariable String name) {
		return "Hello, " + name + "!";
	}

	@RequestMapping(value = "/")
	public ModelAndView index(ModelMap map) {
		if (!map.containsKey("loggedIn")) {
			map.put("loggedIn", false);
		}
		return new ModelAndView("index", map);
	}
	
	@RequestMapping(value = "/login")
	public ModelAndView loginScreen(ModelMap map) {
		map.addAttribute("account", new Account());
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginScreen(@ModelAttribute Account account, ModelMap map) 
			throws EntityDoesNotExistException, AlreadyLoggedInException {
		String username = account.getUsername();
		String password = account.getPassword();
		if (userService.checkIfUserExistsByUsername(username)) {
			User user = userService.retrieveUserByUsername(username);
			try {
				AccountValidator.validate(user, username, password);
			} catch (AccountValidationException e) {
				map.put("error", e.getMessage());
			}
			try {
				userService.logIn(user);
				map.put("loggedIn", true);
				map.put("username", username);
				return new ModelAndView("redirect:/index", map);
			} catch (EntityDoesNotExistException | AlreadyLoggedInException e) {
				map.put("error", e.getMessage());
			}
		} else {
			map.put("error", "The username does not exist");
		}
		return new ModelAndView("login", map);
	}

	@RequestMapping(value = "/logout")
	public ModelAndView logout(@RequestBody String username, ModelMap map) 
			throws EntityDoesNotExistException, AlreadyLoggedOutException {
		try {
			User user = userService.retrieveUserByUsername(username);
			userService.logOut(user);
			map.put("loggedIn", false);
		} catch (EntityDoesNotExistException | AlreadyLoggedOutException e) {
			map.put("error", e.getMessage());
		}
		return new ModelAndView("redirect:/index", map);
	}

}
