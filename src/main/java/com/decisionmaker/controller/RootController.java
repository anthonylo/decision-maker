package com.decisionmaker.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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

import com.decisionmaker.controller.form.ChangePasswordModel;
import com.decisionmaker.domain.user.Account;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.AlreadyLoggedInException;
import com.decisionmaker.exception.AlreadyLoggedOutException;
import com.decisionmaker.exception.DecisionMakerException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.InvalidLoginException;
import com.decisionmaker.service.user.IUserService;
import com.decisionmaker.util.DecisionMakerUtils;
import com.decisionmaker.util.PasswordHash;
import com.decisionmaker.validator.user.AccountValidator;

@Controller
public class RootController {

	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "/name/{name:.+}")
	public @ResponseBody String printHelloWorld(@PathVariable String name) {
		return "Hello, " + name + "!";
	}

	@RequestMapping(value = "/create/anthony", method = RequestMethod.GET)
	public @ResponseBody String createAnthony() 
			throws NoSuchAlgorithmException, InvalidKeySpecException, DecisionMakerException {
		User user = DecisionMakerUtils.createAnthony();
		userService.saveEntity(user);
		return "Anthony Lo was created successfully";
	}
	
	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, ModelMap map) throws EntityDoesNotExistException, DecisionMakerException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if (user != null) {
			user = userService.retrieveEntityById(user.getId());
			session.setAttribute("user", user);
		}
		return new ModelAndView("index", map);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
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
			return new ModelAndView("index", map);
		}
	}

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request, ModelMap map) 
			throws EntityDoesNotExistException, AlreadyLoggedOutException {
		request.getSession().invalidate();
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView viewRegister(ModelMap map) {
		map.addAttribute("user", new User());
		return new ModelAndView("register", map);
	}
	
	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
	public ModelAndView gotoChangePassword(ModelMap map) {
		map.put("changepass", new ChangePasswordModel());
		return new ModelAndView("changepassword", map);
 	}
	
	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public ModelAndView changePassword(@ModelAttribute ChangePasswordModel changepass, 
			HttpServletRequest req, ModelMap map) 
			throws NoSuchAlgorithmException, InvalidKeySpecException, 
				EntityDoesNotExistException, DecisionMakerException {
		if (changepass.isPasswordEmpty()) {
			map.put("error", "One of the password's entered is empty");
			return new ModelAndView("changepassword", map);
		}
		try {
			String username = (String) req.getSession().getAttribute("username");
			userService.validatePasswordByUsername(username, changepass.getOldPassword());

			String newHashedPassword = PasswordHash.createHash(changepass.getNewPassword());
			
			User user = userService.retrieveUserByUsername(username);
			user.getAccount().setPassword(newHashedPassword);
			
			userService.updateEntity(user);
			return new ModelAndView("redirect:/");
		} catch (InvalidLoginException e) {
			map.put("error", e.getMessage());
			return new ModelAndView("changepassword", map);
		}
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerPost(@ModelAttribute User user, ModelMap map) 
			throws DecisionMakerException, NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			userService.saveEntity(user);
			map.put("successful", "The user '" + user.getAccount().getUsername() + "' was created successfully");
			map.put("user", new User());
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