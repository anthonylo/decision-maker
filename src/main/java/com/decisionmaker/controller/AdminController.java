package com.decisionmaker.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.DecisionMakerException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.NotAdministratorException;
import com.decisionmaker.service.user.IUserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/viewusers")
	public ModelAndView viewUsers(HttpServletRequest request, @RequestParam(required=false) Integer page, ModelMap map) {
		User user = (User) request.getSession().getAttribute("user");
		if (!user.getAccount().getAdmin()) {
			return new ModelAndView("redirect:/");
		}
		if (page == null) {
			page = 1;
		}
		Long count = userService.retrieveCount();
		Integer totalPages = (int) ((count+(page-1)) / 25);
		List<User> users = userService.retrieveEntitiesByPageAndCount(page-1, 25);
		
		map.put("users", users);
		map.put("page", page);
		map.put("count", count);
		map.put("totalPages", totalPages+1);
		
		return new ModelAndView("view-all", map);
	}
	
	@RequestMapping(value = "/{username:.+}/delete/{toDelete:.+}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteUserById(@PathVariable String username, @PathVariable String toDelete) 
			throws NotAdministratorException, EntityDoesNotExistException, DecisionMakerException {
		boolean isAdmin = userService.checkIfUserIsAnAdmin(username);
		if (!isAdmin) {
			throw new NotAdministratorException("You are not an administrator");
		}
		userService.deleteUserByUsername(toDelete);
		return "The user '" + toDelete + "' was deleted";
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable Long id, ModelMap map) 
			throws EntityDoesNotExistException, DecisionMakerException {
		
		userService.deleteEntityById(id);
		return new ModelAndView("redirect:/admin/viewusers");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView goToEditUser(@RequestParam Long id, ModelMap map) 
			throws EntityDoesNotExistException, DecisionMakerException {
		User user = userService.retrieveEntityById(id);
		map.put("user", user);
		return new ModelAndView("admin-edit", map);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView editUser(@ModelAttribute User user, ModelMap map) 
			throws EntityDoesNotExistException, DecisionMakerException, 
			NoSuchAlgorithmException, InvalidKeySpecException {
		userService.updateEntity(user);
		map.put("user", user);
		return new ModelAndView("admin-edit", map);
	}
	
	@RequestMapping(value = "/become", method = RequestMethod.GET)
	public ModelAndView becomeAdministrator(HttpServletRequest request) throws EntityDoesNotExistException {
		String username = (String) request.getSession().getAttribute("username");
		userService.makeUserAdministrator(username);
		User user = userService.retrieveUserByUsername(username);
		request.getSession().setAttribute("user", user);
		return new ModelAndView("redirect:/");
	}
	
}