package com.decisionmaker.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class DummyAspect {

	@Before("getIdPointcut()")
	public void getIdAdvice() {
		System.out.println("Get ID for dummyObject");
	}
	
	@Pointcut("execution(* com.decisionmaker.domain.DummyObject.getId())")
	public void getIdPointcut() { }
	
}
