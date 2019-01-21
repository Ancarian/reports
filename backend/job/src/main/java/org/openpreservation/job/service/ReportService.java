package org.openpreservation.job.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.egit.github.core.Milestone;
import org.openpreservation.core.model.config.Organisation;
import org.openpreservation.core.model.report.*;
import org.openpreservation.core.service.PathService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;


public class ReportService {
	private final GithubService githubService;
	private final List<Organisation> organisations;
	private final ObjectMapper mapper;
	private PathService pathService;

	public ReportService(GithubService githubService, List<Organisation> organisations,
						 ObjectMapper mapper, PathService pathService) {
		this.githubService = githubService;
		this.organisations = organisations;
		this.mapper = mapper;
		this.pathService = pathService;
	}

	public void startJob() {
		LocalDate reportName = LocalDate.now();
		for (Organisation organisation : organisations) {
			String user = organisation.getOrganisation();
			for (String repository : organisation.getProjects()) {
				Report report = constructReport(user, repository);
				if (report != null) {
					save(user, repository, report, reportName.toString() + ".json");
				}
			}
		}
	}

	private void save(String organisation, String repository, Report report, String reportName) {
		try {
			File newReport = pathService.getReportPath(organisation, repository, reportName);
			if (!newReport.exists()) {
				Files.createDirectories(newReport.getParentFile().toPath());
				newReport.createNewFile();
				mapper.writeValue(newReport, report);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Report constructReport(String user, String repository) {
		try {
			List<CustomIssue> issues = githubService.getOpenedIssues(user, repository);
			List<MilestoneReportPart> reportMilestonesPart = constructMilestoneReportParts(user, repository);
			return constructReport(issues, reportMilestonesPart);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<MilestoneReportPart> constructMilestoneReportParts(String user, String repository) throws IOException {
		List<Milestone> milestones = githubService.getMilestones(user, repository);
		List<MilestoneReportPart> customMilestones = new ArrayList<>();
		for (Milestone milestone : milestones) {
			List<CustomIssue> issues = githubService.getOpenedIssues(user, repository, milestone.getNumber());
			List<CustomPullRequest> pullRequests = parsePullRequestsIds(issues).stream()
					.map(id -> githubService.getPullRequest(user, repository, id))
					.filter(pr -> pr != null && !pr.isMerged() && pr.getState().equals("open"))
					.collect(Collectors.toList());
			MilestoneReportPart milestoneReportPart = new MilestoneReportPart(milestone, issues, pullRequests);
			customMilestones.add(milestoneReportPart);
		}
		return customMilestones;
	}

	private List<Integer> parsePullRequestsIds(List<CustomIssue> issues) {
		return issues.stream().filter(issue -> issue.getPullRequestUrl() != null).map(issue -> {
			String[] id = issue.getPullRequestUrl().split("/");
			return Integer.parseInt(id[id.length - 1]);
		}).collect(Collectors.toList());
	}

	private Report constructReport(List<CustomIssue> issues, List<MilestoneReportPart> reportMilestonesPart) {
		ProjectReportPart projectReportPart = new ProjectReportPart(issues, reportMilestonesPart);
		Map<String, List<MilestoneReportPart>> releases = reportMilestonesPart.stream()
				.filter(milestone -> milestone.getShortVersionType() != null)
				.sorted((o1, o2) -> o2.getShortVersionType()
						.compareTo(o1.getShortVersionType()))
				.collect(groupingBy(MilestoneReportPart::getVersion));
		return new Report(projectReportPart, releases);
	}
}



