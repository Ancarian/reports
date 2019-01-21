package org.openpreservation.core.model.report;

import lombok.Data;
import org.eclipse.egit.github.core.PullRequest;

import java.util.Date;
import java.util.List;

@Data
public class CustomPullRequest {
	private String url;
	private String title;
	private long id;
	private boolean isMerged;
	private boolean isOverDue;
	private String state;
	private float outstandingEffort;
	private List<CustomIssue> associatedIssues;
	
	public CustomPullRequest() {
	}

	public CustomPullRequest(PullRequest pullRequest, List<CustomIssue> associatedIssues) {
		this.id = pullRequest.getId();
		this.isMerged = pullRequest.isMerged();
		this.title = pullRequest.getTitle();
		this.state = pullRequest.getState();
		this.url = pullRequest.getHtmlUrl();
		this.associatedIssues = associatedIssues;
		this.outstandingEffort = associatedIssues.stream().map(CustomIssue::getEstimation).reduce(0f, Float::sum);
		this.isOverDue = pullRequest.getMilestone().getDueOn().before(new Date());
	}
}
