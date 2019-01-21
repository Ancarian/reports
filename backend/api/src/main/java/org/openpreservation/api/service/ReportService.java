package org.openpreservation.api.service;

import org.openpreservation.api.dto.ReportSummary;
import org.openpreservation.api.dto.RepositorySummary;
import org.openpreservation.api.exception.NotFoundException;
import org.openpreservation.api.utils.Converter;

import org.openpreservation.core.model.config.Organisation;
import org.openpreservation.core.model.report.Report;
import org.openpreservation.core.service.PathService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.openpreservation.api.utils.FileWorker.getFileNames;

@Service
public class ReportService {
	private final Converter<Report, File> fileConverter;
	private final List<Organisation> organisations;
	private final PathService pathService;

	public ReportService(Converter<Report, File> fileConverter, List<Organisation> organisations, PathService pathService) {
		this.fileConverter = fileConverter;
		this.organisations = organisations;
		this.pathService = pathService;
	}

	public List<ReportSummary> getLastReports() {
		List<ReportSummary> links = new ArrayList<>();
		for (Organisation organisation : organisations) {
			List<RepositorySummary> reportRepositorySummaries = new ArrayList<>();
			String org = organisation.getOrganisation();
			for (String repository : organisation.getProjects()) {
				File projectDirectory = pathService.getProjectDirectoryPath(org, repository);
				if (projectDirectory.exists()) {
					List<String> fileNames = getFileNames(projectDirectory.toPath());
					if (!fileNames.isEmpty()) {
						reportRepositorySummaries.add(new RepositorySummary(repository,
								Collections.singletonList(fileNames.get(0))));
					}
				}
			}
			links.add(new ReportSummary(org, reportRepositorySummaries));
		}
		return links;
	}

	public ReportSummary getReports(String user, String repository) {
		File projectDirectory = pathService.getProjectDirectoryPath(user, repository);
		if (!projectDirectory.exists()) {
			throw new NotFoundException("Incorrect user or repository");
		}
		List<String> fileNames = getFileNames(projectDirectory.toPath());
		return new ReportSummary(user, Collections.singletonList(new RepositorySummary(repository, fileNames)));
	}

	public Report getReport(String user, String repository, LocalDate reportDate) {
		File report = pathService.getReportPath(user, repository, reportDate + ".json");
		if (!report.exists()) {
			throw new NotFoundException("Incorrect user, repository or reportDate");
		}
		return fileConverter.convert(report);
	}
}



