package org.openpreservation.core.config;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import java.text.SimpleDateFormat;

public class ObjectMapperConfiguration {

	public static ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.setDateFormat(df);
		return mapper;
	}
}
