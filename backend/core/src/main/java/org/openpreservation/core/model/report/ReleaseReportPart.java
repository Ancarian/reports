package org.openpreservation.core.model.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReleaseReportPart {
	private final List<CustomIssue.IssueStatus> STATUSES = Arrays.asList(CustomIssue.IssueStatus.values());

	private String version;
	private Date createdAt;
	private List<CustomIssue> customIssues;

	public ReleaseReportPart() {

	}

	public ReleaseReportPart(String version, Date createdAt, List<CustomIssue> customIssues) {
		this.version = version;
		this.createdAt = createdAt;
		this.customIssues = new ArrayList<>();
		this.customIssues= customIssues;
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
