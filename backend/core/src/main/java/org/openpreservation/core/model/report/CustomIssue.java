package org.openpreservation.core.model.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
public class CustomIssue {
    @JsonIgnore
    private final Pattern pattern = Pattern.compile("(\\d+.[0-9]+)D|(\\d+)D");
    private long id;
    private String title;
    private String htmlUrl;
    private String user;
    private String state;
    private List<String> labels;
    private float estimation;
    private String pullRequestUrl;
    private String milestoneTitle;
    private IssueStatus issueStatus;

    public CustomIssue() {
    }

    public CustomIssue(Issue issue) {
        this.id = issue.getNumber();
        this.state = issue.getState();
        this.title = issue.getTitle();
        this.htmlUrl = issue.getHtmlUrl();
        this.user = issue.getUser().getHtmlUrl();
        this.pullRequestUrl = issue.getPullRequest().getHtmlUrl();
        this.labels = issue.getLabels().stream().map(Label::getName).collect(Collectors.toList());
        this.milestoneTitle = issue.getMilestone() == null ? null : issue.getMilestone().getTitle();

        Matcher m = pattern.matcher(issue.getBody());
        if (m.find()) {
            estimation = Float.parseFloat(m.group());
        }

        if (pullRequestUrl == null && "open".equals(state)){
            issueStatus = IssueStatus.NOT_ASSOCIATED;
        } else if (pullRequestUrl != null && "open".equals(state)){
            issueStatus = IssueStatus.ASSOCIATED;
        } else {
            issueStatus = IssueStatus.MERGED;
        }
    }


    public enum IssueStatus{
        NOT_ASSOCIATED, ASSOCIATED, MERGED
    }
}
