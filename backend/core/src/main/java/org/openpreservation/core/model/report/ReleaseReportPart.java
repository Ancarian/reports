package org.openpreservation.core.model.report;

import lombok.Data;

import java.util.List;

@Data
public class ReleaseReportPart {
	private String version;
	private List<MilestoneReportPart> milestones;
	private float totalEffort;
	private float outstandingEffort;
	private boolean isHasOverduePullRequests;

	public ReleaseReportPart() {

	}

	public ReleaseReportPart(List<MilestoneReportPart> releases) {
		version = releases.get(0).getVersion();
		milestones = releases;
		totalEffort = milestones.stream().map(MilestoneReportPart::getTotalEffort).reduce(0f, Float::sum);
		outstandingEffort = milestones.stream().map(MilestoneReportPart::getOutstandingEffort).reduce(0f, Float::sum);
		isHasOverduePullRequests = milestones.stream().anyMatch(MilestoneReportPart::isHasOverduePullRequests);
	}
}
