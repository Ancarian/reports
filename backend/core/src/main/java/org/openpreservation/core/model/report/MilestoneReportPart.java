package org.openpreservation.core.model.report;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.eclipse.egit.github.core.Milestone;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class MilestoneReportPart {
    @JsonIgnore
    private final Pattern pattern = Pattern.compile("(\\d+.\\d+)-(m[1-4])");
    private boolean isHasOverduePullRequests;
    private float totalEffort;
    private float outstandingEffort;
    private String url;
    private String title;
    private String version;
    private String shortVersionType;
    private String fullVersion;
    private Date dueOn;
    @JsonIgnore
    private List<CustomIssue> issues;
    private List<CustomPullRequest> openedPullRequests;

    public MilestoneReportPart() {
    }

    public MilestoneReportPart(Milestone milestone, List<CustomIssue> issues, List<CustomPullRequest> pullRequests) {
        this.url = milestone.getUrl();
        this.title = milestone.getTitle();
        this.dueOn = milestone.getDueOn();
        this.issues = issues;
        this.openedPullRequests = pullRequests;

        Matcher m = pattern.matcher(this.title);
        if (m.find()) {
            this.fullVersion = m.group(0);
            this.version = m.group(1);
            this.shortVersionType = m.group(2);
        }

        totalEffort = this.issues.stream().map(CustomIssue::getEstimation).reduce(0f, Float::sum);
        this.outstandingEffort = this.openedPullRequests.stream()
                .map(CustomPullRequest::getOutstandingEffort).reduce(0f, Float::sum);
        this.isHasOverduePullRequests = this.openedPullRequests.stream().anyMatch(CustomPullRequest::isOverDue);
    }
}
