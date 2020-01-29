package org.openpreservation.job.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
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
			List<MilestoneReportPart> latestRelease = separateMilestonesByRelease(milestones);
			ReleaseReportPart releaseReportPart = createReleaseReportParts(latestRelease);
			List<CustomIssue> backLog = githubService.getOpenedIssues(user, repository, "none");
			return new Report(backLog, releaseReportPart);
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

	private List<MilestoneReportPart> separateMilestonesByRelease(List<MilestoneReportPart> milestones) {
		Map<String, List<MilestoneReportPart>> releases = milestones.stream()
		                                                            .filter(milestone -> milestone.getShortVersionType() != null)
		                                                            .collect(groupingBy(MilestoneReportPart::getVersion));

		String latestVersionName = releases.keySet().stream().max(Comparator.comparing(DefaultArtifactVersion::new)).orElse(null);
		return latestVersionName == null ? Collections.emptyList(): releases.get(latestVersionName);
	}

	private ReleaseReportPart createReleaseReportParts(List<MilestoneReportPart> release) {
		if (release.isEmpty()) {
			return null;
		}
		MilestoneReportPart initialMilestone = release.get(0);
		List<CustomIssue> customIssues = new ArrayList<>();
		release.forEach(milestoneReportPart -> customIssues.addAll(milestoneReportPart.getIssues()));
		return new ReleaseReportPart(initialMilestone.getVersion(),
		                             initialMilestone.getCreatedAt(),
		                             customIssues.stream()
		                                         .sorted(Comparator.comparingInt(o -> STATUSES.indexOf(o.getIssueStatus())))
		                                         .collect(toList()));
	}

	;
}



