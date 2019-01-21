package org.openpreservation.core.model.report;


import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProjectReportPart {
	private final static List<String> typeLabels = Arrays.asList("P0", "P1", "P2", "P3");
	private final static List<String> bugLabels = Arrays.asList("bug", "feature");
	private List<CustomIssue> incomingIssues;
	private List<CustomIssue> backlog;
	private List<MilestoneReportPart> releases;

	public ProjectReportPart() {
	}

	public ProjectReportPart(List<CustomIssue> issues, List<MilestoneReportPart> milestones) {
		incomingIssues = new ArrayList<>();
		backlog = new ArrayList<>();
		releases = milestones.stream().filter(milestone -> "m4".equals(milestone.getShortVersionType()) &&
				milestone.getDueOn() != null)
				.collect(Collectors.toList());

		issues.forEach(this::addIssue);
	}

	private void addIssue(CustomIssue issue) {
		List<String> labels = issue.getLabels();
		boolean hasBugLabel = CollectionUtils.containsAny(labels, bugLabels);
		boolean hasTypeLabel = CollectionUtils.containsAny(labels, typeLabels);
		if (labels.isEmpty() || (hasTypeLabel && !hasBugLabel) || (!hasTypeLabel && hasBugLabel)) {
			incomingIssues.add(issue);
		} else if (hasTypeLabel && (issue.getMilestoneTitle() == null || !issue.getMilestoneTitle().contains("m4"))) {
			backlog.add(issue);
		}
	}
}
