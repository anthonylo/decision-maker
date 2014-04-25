package com.decision.maker;

import org.apache.log4j.Logger;
import org.junit.Test;

public class MiscellaneousTest {
	
	private Logger log = Logger.getLogger(MiscellaneousTest.class);
	
	@Test
	public void check_location_of_ojdbc_driver() {
		log.info("OJDBC Driver: " + oracle.jdbc.driver.OracleDriver.class);
	}
	
}
