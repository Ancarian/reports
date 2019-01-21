package org.openpreservation.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.openpreservation.core.config.ObjectMapperConfiguration;
import org.openpreservation.core.model.config.Organisation;
import org.openpreservation.core.service.PathService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@ComponentScan("org.openpreservation")
public class ReportsApplication {

	@Value("${reports.config.filepath}")
	private String configPath;
	@Value("${reports.root}")
	private String rootPath;

	public static void main(String[] args) {
		SpringApplication.run(ReportsApplication.class, args);
	}

	@Bean
	public List<Organisation> organisations(ObjectMapper mapper) throws IOException {
		List<Organisation> organisations;
		organisations = Arrays.asList(mapper.readValue(new File(configPath), Organisation[].class));
		if (organisations.isEmpty()) {
			throw new IllegalStateException("At least one repository required");
		}
		return organisations;
	}

	@Bean
	public PathService pathService(){
		return new PathService(rootPath);
	}

	@Bean
	public ObjectMapper objectMapper(){
		return ObjectMapperConfiguration.objectMapper();
	}
}

