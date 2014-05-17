package com.decisionmaker.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.AlreadyLoggedOutException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.service.user.IUserService;

@WebListener
public class DMSessionListener implements HttpSessionListener, ServletContextListener {

	private IUserService userService;
	
	private static Logger log = Logger.getLogger(DMSessionListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		setUserService(se);

		HttpSession session = se.getSession();
		log.info("Session " + session.getId() + " has been created");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		setUserService(se);

		HttpSession session = se.getSession();
		if (!StringUtils.isEmpty(session.getAttribute("username"))) {
			User user = (User) session.getAttribute("user");
			log.info("Session " + session.getId() + " has been destroyed");
			try {
				userService.logOut(user);
			} catch (EntityDoesNotExistException | AlreadyLoggedOutException e) {
				log.error("An exception occurred: " + e.getMessage(), e);
			}
		}
	}
	
	private void setUserService(HttpSessionEvent se) {
		if (userService != null) return;
		HttpSession session = se.getSession();
		
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(session.getServletContext());
		this.userService = (IUserService) ctx.getBean("userService");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
