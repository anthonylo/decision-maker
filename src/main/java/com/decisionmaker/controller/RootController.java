package com.decisionmaker.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.decisionmaker.domain.user.Account;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.AlreadyLoggedInException;
import com.decisionmaker.exception.AlreadyLoggedOutException;
import com.decisionmaker.exception.DecisionMakerException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.InvalidLoginException;
import com.decisionmaker.service.user.IUserService;
import com.decisionmaker.validator.user.AccountValidator;

@Controller
public class RootController {

	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "/name/{name:.+}")
	public @ResponseBody String printHelloWorld(@PathVariable String name) {
		return "Hello, " + name + "!";
	}

	@RequestMapping(value = {"/", "/index"})
	public ModelAndView index(HttpServletRequest request, ModelMap map) {
		return new ModelAndView("index", map);
	}
	
//	@RequestMapping(value = "/login")
//	public ModelAndView loginScreen(ModelMap map) {
//		map.addAttribute("account", new User());
//		return new ModelAndView("login");
//	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginScreen(@ModelAttribute Account account, 
			RedirectAttributes redirectAttributes, HttpServletRequest request, ModelMap map) 
			throws EntityDoesNotExistException, AlreadyLoggedInException,
				NoSuchAlgorithmException, InvalidKeySpecException, InvalidLoginException {
		String username = account.getUsername();
		String password = account.getPassword();
		HttpSession session = request.getSession();
	
		try {
			AccountValidator.validate(username, password);
			User user = userService.retrieveUserByUsername(username);
			userService.logIn(user);
			session.setAttribute("loggedIn", true);
			session.setAttribute("username", username);
			session.setAttribute("user", user);
			return new ModelAndView("redirect:/");
		} catch (Exception e) {
			map.put("error", e.getMessage());
			return new ModelAndView("/", map);
		}
	}

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request, ModelMap map) 
			throws EntityDoesNotExistException, AlreadyLoggedOutException {
		HttpSession session = request.getSession();
		try {
			String username = (String) session.getAttribute("username");
			User user = userService.retrieveUserByUsername(username);
			userService.logOut(user);
			session.removeAttribute("user");
			session.removeAttribute("username");
			session.removeAttribute("loggedIn");
		} catch (EntityDoesNotExistException | AlreadyLoggedOutException e) {
			map.put("error", e.getMessage());
		}
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView viewRegister(ModelMap map) {
		map.addAttribute("user", new User());
		return new ModelAndView("register", map);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerPost(@ModelAttribute User user, ModelMap map) 
			throws DecisionMakerException, NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			userService.saveEntity(user);
			map.put("successful", "The user '" + user.getAccount().getUsername() + "' was created successfully");
		} catch (DecisionMakerException e) {
			map.put("error", e.getMessage());
			map.put("user", user);
		}
		return new ModelAndView("register", map);
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewUser(HttpServletRequest request, ModelMap map) throws EntityDoesNotExistException {
		User user = (User) request.getSession().getAttribute("user");
		map.put("user", user);
		return new ModelAndView("view-user", map);
	}
	
	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public @ResponseBody String refreshUser(@RequestParam String username, HttpServletRequest request) throws EntityDoesNotExistException {
		User user = userService.retrieveUserByUsername(username);
		request.getSession().setAttribute("user", user);
		return "The user '" + username + "' has been refreshed"; 
	}
	
}