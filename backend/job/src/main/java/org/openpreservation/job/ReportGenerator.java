package org.openpreservation.job;


import com.beust.jcommander.JCommander;
import org.openpreservation.core.config.ObjectMapperConfiguration;
import org.openpreservation.core.model.config.Organisation;
import org.openpreservation.core.service.PathService;
import org.openpreservation.job.config.Args;
import org.openpreservation.job.config.GithubConfiguration;
import org.openpreservation.job.service.GithubService;
import org.openpreservation.job.service.ReportService;
import org.openpreservation.job.utils.PropertiesReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class ReportGenerator {

	public static void main(String[] args) {
		PropertiesReader propertiesReader = new PropertiesReader();
		ObjectMapper mapper = ObjectMapperConfiguration.objectMapper();

		Args arguments = new Args();
		JCommander.newBuilder().addObject(arguments).build().parse(args);

		List<Organisation> organisations = constructConfiguration(mapper, arguments.getConfig());
		GithubService githubService = GithubConfiguration.constructGitHubService(propertiesReader);

		githubService.validateConfiguration(organisations);
		PathService pathService = new PathService(arguments.getRoot());
		ReportService reportService = new ReportService(githubService, organisations, mapper, pathService);
		reportService.startJob();
	}

	public static List<Organisation> constructConfiguration(ObjectMapper mapper, String configFilePath){
		try {
			List<Organisation> organisations = Arrays.asList(mapper.readValue(new File(configFilePath),
					Organisation[].class));
			if (organisations.isEmpty()) {
				throw new IllegalStateException("At least one repository required");
			}
			return organisations;
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage());
		}
	}
}
