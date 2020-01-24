package org.openpreservation.job.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.openpreservation.core.model.config.Organisation;
import org.openpreservation.core.model.report.CustomIssue;
import org.openpreservation.core.model.report.MilestoneReportPart;
import org.openpreservation.core.model.report.ReleaseReportPart;
import org.openpreservation.core.model.report.Report;
import org.openpreservation.core.service.PathService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;


public class ReportService {
	private final GithubService githubService;
	private final List<Organisation> organisations;
	private final ObjectMapper mapper;
	private final List<CustomIssue.IssueStatus> STATUSES = Arrays.asList(CustomIssue.IssueStatus.values());
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
			List<MilestoneReportPart> milestones = getReleaseMilestones(user, repository);
			initializeMilestoneIssues(user, repository, milestones);
			Map<String, List<MilestoneReportPart>> releases = seperateMilestonesByRelease(milestones);
			List<ReleaseReportPart> releaseReportParts = createReleaseReportParts(releases);
			List<CustomIssue> backLog = githubService.getOpenedIssues(user, repository, "none");
			return new Report(backLog, releaseReportParts);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<MilestoneReportPart> getReleaseMilestones(String user, String repository) throws IOException {
		return githubService.getMilestones(user, repository)
		                    .stream()
		                    .map(MilestoneReportPart::new)
		                    .filter(MilestoneReportPart::isReleaseMilestone)
		                    .collect(toList());
	}

	private void initializeMilestoneIssues(String user, String repository, List<MilestoneReportPart> milestones) throws IOException {
		for (MilestoneReportPart part : milestones) {
			part.setIssues(new ArrayList<>(githubService.getAllIssues(user, repository, String.valueOf(part.getNumber()))));
		}
	}

	private Map<String, List<MilestoneReportPart>> seperateMilestonesByRelease(List<MilestoneReportPart> milestones) {
		return milestones.stream()
		                 .filter(milestone -> milestone.getShortVersionType() != null)
		                 .sorted((o1, o2) -> o2.getShortVersionType()
		                                       .compareTo(o1.getShortVersionType()))
		                 .collect(groupingBy(MilestoneReportPart::getVersion));
	}

	private List<ReleaseReportPart> createReleaseReportParts(Map<String, List<MilestoneReportPart>> releases){
		return releases.values().stream().map(milestonesParts -> {
			MilestoneReportPart initialMilestone = milestonesParts.get(0);
			List<CustomIssue> customIssues = new ArrayList<>();
			milestonesParts.forEach(milestoneReportPart -> customIssues.addAll(milestoneReportPart.getIssues()));
			return new ReleaseReportPart(initialMilestone.getVersion(),
			                             initialMilestone.getCreatedAt(),
			                             customIssues.stream()
			                                         .sorted(Comparator.comparingInt(o -> STATUSES.indexOf(o.getIssueStatus())))
			                                         .collect(toList()));
		}).collect(toList());
	}
}



