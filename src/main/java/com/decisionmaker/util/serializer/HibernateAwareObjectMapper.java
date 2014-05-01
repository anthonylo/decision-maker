package com.decision.maker.util.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class HibernateAwareObjectMapper extends ObjectMapper {

    /**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = -6577000347376716599L;

	public HibernateAwareObjectMapper() {
        registerModule(new Hibernate4Module());
    }
}