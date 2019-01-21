package org.openpreservation.job.config;

import org.openpreservation.job.service.GithubService;
import org.openpreservation.job.utils.PropertiesReader;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.MilestoneService;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;

public class GithubConfiguration {

	public static GithubService constructGitHubService(PropertiesReader propertiesReader) {
		GitHubClient gitHubClient = gitHubClient(
				propertiesReader.readProperty("github.user"),
				propertiesReader.readProperty("github.password")
		);
		IssueService issueService = issueService(gitHubClient);
		MilestoneService milestoneService = milestoneService(gitHubClient);
		PullRequestService pullRequestService = pullRequestService(gitHubClient);
		RepositoryService repositoryService = repositoryService(gitHubClient);
		return new GithubService(issueService, milestoneService, pullRequestService, repositoryService);
	}

	private static GitHubClient gitHubClient(String login, String password) {
		if (login.isEmpty() || password.isEmpty()){
			throw new IllegalArgumentException("bad credentials");
		}
		GitHubClient client = new GitHubClient();
		client.setCredentials(login, password);
		return client;
	}

	private static IssueService issueService(GitHubClient client) {
		return new IssueService(client);
	}

	private static MilestoneService milestoneService(GitHubClient client) {
		return new MilestoneService(client);
	}

	private static PullRequestService pullRequestService(GitHubClient client) {
		return new PullRequestService(client);
	}

	private static RepositoryService repositoryService(GitHubClient client) {
		return new RepositoryService(client);
	}
}
