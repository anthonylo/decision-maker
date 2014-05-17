package com.decisionmaker.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.DecisionMakerException;
import com.decisionmaker.exception.EntityDoesNotExistException;
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
		Integer totalPages = page % 25;
		List<User> users = userService.retrieveEntitiesByPageAndCount(page-1, 25);
		
		map.put("users", users);
		map.put("page", page);
		map.put("count", count);
		map.put("totalPages", totalPages);
		
		return new ModelAndView("view-all", map);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteUser(@RequestParam Long id, ModelMap map) throws EntityDoesNotExistException, DecisionMakerException {
		userService.deleteEntityById(id);
		return new ModelAndView("redirect:/admin/viewusers");
	}
	
}
