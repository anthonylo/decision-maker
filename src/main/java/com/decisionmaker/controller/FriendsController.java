package com.decisionmaker.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.decisionmaker.domain.user.FriendRequest;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.AlreadyFriendsException;
import com.decisionmaker.exception.DecisionMakerException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.IllegalFriendException;
import com.decisionmaker.exception.IncorrectUserException;
import com.decisionmaker.service.user.IUserService;

@Controller
@RequestMapping(value = "friends")
public class FriendsController {

	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView goToAddFriendsPage() {
		return new ModelAndView("friends/add");
	}
	
	@RequestMapping(value = "view")
	public ModelAndView viewFriends(HttpServletRequest request, ModelMap map) {
		User user = (User) request.getSession().getAttribute("user");
		map.put("friends", user.getFriends());
		return new ModelAndView("friends/view", map);
	}
	
	@RequestMapping(value = "/request/{userId}", method = RequestMethod.POST)
	public @ResponseBody String sendFriendRequest(@RequestBody FriendRequest friendRequest,
			HttpServletRequest request, @PathVariable Long userId) 
					throws NoSuchAlgorithmException, InvalidKeySpecException, 
					DecisionMakerException, EntityDoesNotExistException, IncorrectUserException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user.getId().equals(userId)) {
			userService.sendFriendRequest(friendRequest);
			user = userService.retrieveEntityById(userId);
			request.getSession().setAttribute("user", user);
			return "The friend request was successful";
		} else {
			throw new IncorrectUserException("This is not the right user");
		}
	}

	@RequestMapping(value = "/request/accept", method = RequestMethod.POST)
	public @ResponseBody String acceptFriendRequest(@RequestParam String friendUsername, 
			@RequestParam Long friendId, HttpServletRequest request) 
					throws DecisionMakerException, EntityDoesNotExistException,
						AlreadyFriendsException, IllegalFriendException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		userService.addFriend(friendId, user.getId());
		user = userService.retrieveEntityById(user.getId());
		request.getSession().setAttribute("user", user);
		return "The friend request was accepted successfully";
	}

	@RequestMapping(value = "/request/reject", method = RequestMethod.POST)
	public @ResponseBody String rejectFriendRequest(@RequestParam Long friendId, 
			HttpServletRequest request) throws DecisionMakerException, EntityDoesNotExistException, 
						AlreadyFriendsException, IllegalFriendException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		userService.cancelFriendRequest(friendId, user.getId());
		user = userService.retrieveEntityById(user.getId());
		request.getSession().setAttribute("user", user);
		return "The friend request was accepted successfully";
	}
	
	@RequestMapping(value = "/request/cancel", method = RequestMethod.POST)
	public @ResponseBody String cancelFriendRequest(@RequestParam Long friendId, 
			HttpServletRequest request) throws DecisionMakerException, EntityDoesNotExistException, 
						AlreadyFriendsException, IllegalFriendException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		userService.cancelFriendRequest(user.getId(), friendId);
		user = userService.retrieveEntityById(user.getId());
		request.getSession().setAttribute("user", user);
		return "The friend request was accepted successfully";
	}
	
	@RequestMapping(value = "/view/requests")
	public ModelAndView viewFriendRequests(HttpServletRequest request, ModelMap map) {
		return new ModelAndView("friends/view-requests", map);
	}
	
	@RequestMapping(value = "message/send")
	public ModelAndView goToSendMessage(HttpServletRequest request, ModelMap map) {
		User user = (User) request.getSession().getAttribute("user");
		map.put("friends", user.getFriends());
		return new ModelAndView("friends/send-message", map);
	}
	
	@RequestMapping(value = "name/{query}", method = RequestMethod.GET)
	public @ResponseBody List<User> retrievePossibleUsers(HttpServletRequest request, @PathVariable String query, 
			@RequestParam(required = false, defaultValue = "0") Integer page, 
			@RequestParam(required = false, defaultValue = "0") Integer count) {
		Long id = ((User) request.getSession().getAttribute("user")).getId();
		return userService.getUsersWhoArentFriends(id, query, page*count, count);
	}
	
}