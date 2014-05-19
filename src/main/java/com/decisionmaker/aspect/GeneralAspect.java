package com.decisionmaker.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class GeneralAspect {

	private static Logger log = Logger.getLogger(GeneralAspect.class);
	
	@Before("execution(* com.decisionmaker.domain.user.User.getId())")
	public void getUserIdAdvice() {
		log.info("Executing Before Advice on com.decisionmaker.domain.user.User.getId()");
	}
	
    @Before("getLongIdPointcut()")
    public void secondAdvice(){
        System.out.println("Executing secondAdvice on getId()");
    }
    
	@Before("allMethodsInUserPointcut()")
	public void allUserMethodsAdvice() {
		log.info("Call this with any method call for com.decisionmaker.domain.user.*");
	}
	
    @Pointcut("execution(public Long getId())")
    public void getLongIdPointcut(){}
	
    @Pointcut("within(com.decisionmaker.domain.user.*)")
    public void allMethodsInUserPointcut(){}
}
