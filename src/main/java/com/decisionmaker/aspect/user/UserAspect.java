package com.decisionmaker.aspect.user;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserAspect {

	@Before("retrieveUserByIdPointcut()")
	public void should_log_me() {
		System.out.println("test : should_log_me");
	}
	
	@Pointcut("execution(* com.decisionmaker.controller.UserController.retrieveUserById(..))")
	public void retrieveUserByIdPointcut() { }
	
}
