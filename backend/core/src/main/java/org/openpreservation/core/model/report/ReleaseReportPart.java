package org.openpreservation.core.model.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReleaseReportPart {
	private String version;
	private Date createdAt;
	private List<CustomIssue> customIssues;

	public ReleaseReportPart() {

	}

	public ReleaseReportPart(List<MilestoneReportPart> milestones) {
		MilestoneReportPart initiatedMilestone = milestones.get(0);
		this.version = initiatedMilestone.getVersion();
		this.createdAt = initiatedMilestone.getCreatedAt();
		this.customIssues = new ArrayList<>();
		milestones.forEach(part -> this.customIssues.addAll(part.getIssues()));
	}

	public int getIssueCount(){
		return customIssues.size();
	}

	public double getIntegrationTime(){
		return customIssues.size() * 0.5;
	}

	public float getTotalDevelopmentTime(){
		return customIssues.stream().map(CustomIssue::getEstimation).reduce(0f, Float::sum);
	}
}
