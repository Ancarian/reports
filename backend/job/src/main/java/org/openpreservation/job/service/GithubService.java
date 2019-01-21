package org.openpreservation.job.service;



import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.MilestoneService;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.openpreservation.core.model.config.Organisation;
import org.openpreservation.core.model.report.CustomIssue;
import org.openpreservation.core.model.report.CustomPullRequest;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GithubService {
	private final IssueService issueService;
	private final MilestoneService milestoneService;
	private final PullRequestService pullRequestService;
	private final RepositoryService repositoryService;

	public GithubService(IssueService issueService, MilestoneService milestoneService,
						 PullRequestService pullRequestService, RepositoryService repositoryService) {
		this.issueService = issueService;
		this.milestoneService = milestoneService;
		this.pullRequestService = pullRequestService;
		this.repositoryService = repositoryService;
	}


	public List<CustomIssue> getOpenedIssues(String user, String repository) throws IOException {
		return getIssues(user, repository, null);
	}

	public List<CustomIssue> getOpenedIssues(String user, String repository, int milestoneId) throws IOException {
		Map<String, String> params = new HashMap<>();
		params.put("milestone", "" + milestoneId);
		return getIssues(user, repository, params);
	}

	private List<CustomIssue> getIssues(String user, String repository, Map<String, String> params) throws IOException {
		List<Issue> openedIssues = issueService.getIssues(user, repository, params);
		return openedIssues.stream().map(CustomIssue::new).collect(Collectors.toList());
	}

	private CustomIssue getIssue(String user, String repository, int number) {
		try {
			Issue issue = issueService.getIssue(user, repository, number);
			return new CustomIssue(issue);
		} catch (IOException e) {
			return null;
		}
	}

	public List<Milestone> getMilestones(String user, String repository) throws IOException {
		return milestoneService.getMilestones(user, repository, null);
	}


	public CustomPullRequest getPullRequest(String user, String repository, int id){
		try {
			PullRequest request = pullRequestService.getPullRequest(RepositoryId.create(user, repository), id);
			List<CustomIssue> associatedIssues = parseAssociatedIssues(request.getBody()).stream()
					.map(issueNumber -> getIssue(user, repository, issueNumber))
					.filter(Objects::nonNull).collect(Collectors.toList());
			return new CustomPullRequest(request, associatedIssues);
		} catch (IOException e) {
			return null;
		}
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

	private List<Integer> parseAssociatedIssues(String description) {
		List<Integer> associatedIssues = new ArrayList<>();
		Pattern pattern = Pattern.compile("#(\\d+)");
		Matcher m = pattern.matcher(description);
		int start = 0;
		while (m.find(start)) {
			associatedIssues.add(Integer.parseInt(m.group(1)));
			start = m.start() + 1;
		}

		return associatedIssues;
	}
}
