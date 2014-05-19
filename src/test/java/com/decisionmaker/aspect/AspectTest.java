package com.decisionmaker.aspect;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.decisionmaker.domain.DummyObject;

public class AspectTest {
	
	private static Logger log = Logger.getLogger(AspectTest.class);
	
	@Test
	public void get_id_should_trigger_user_get_id_advice() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/test-applicationContextAop.xml");
		DummyObject dummyObj = (DummyObject) ctx.getBean("dummyObject");
		log.info("dummyObject ID : " + dummyObj.getId());
		((ConfigurableApplicationContext)ctx).close();
	}

}
