package org.openpreservation.job.service;


import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.MilestoneService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.openpreservation.core.model.config.Organisation;
import org.openpreservation.core.model.report.CustomIssue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GithubService {
	private final IssueService issueService;
	private final MilestoneService milestoneService;
	private final RepositoryService repositoryService;

	public GithubService(IssueService issueService, MilestoneService milestoneService, RepositoryService repositoryService) {
		this.issueService = issueService;
		this.milestoneService = milestoneService;
		this.repositoryService = repositoryService;
	}

	public List<CustomIssue> getAllIssues(String user, String repository, String milestoneId) throws IOException {
		Map<String, String> params = new HashMap<>();
		params.put("milestone", milestoneId);
		params.put("state", "all");
		return getIssues(user, repository, params);
	}

	public List<CustomIssue> getOpenedIssues(String user, String repository, String milestoneId) throws IOException {
		Map<String, String> params = new HashMap<>();
		params.put("milestone", milestoneId);
		return getIssues(user, repository, params);
	}

	private List<CustomIssue> getIssues(String user, String repository, Map<String, String> params) throws IOException {
		List<Issue> openedIssues = issueService.getIssues(user, repository, params);
		return openedIssues.stream().map(CustomIssue::new).collect(Collectors.toList());
	}

	public List<Milestone> getMilestones(String user, String repository) throws IOException {
		return milestoneService.getMilestones(user, repository, null);
	}


	public void validateConfiguration(List<Organisation> organisations) {
		for (Organisation report : organisations) {
			String user = report.getOrganisation();
			for (String repository : report.getProjects()) {
				try {
					repositoryService.getRepository(user, repository);
				} catch (IOException e) {
					throw new IllegalStateException(
							String.format("Incorrect state: %s", e.getMessage())
					);
				}
			}
		}
	}
}
