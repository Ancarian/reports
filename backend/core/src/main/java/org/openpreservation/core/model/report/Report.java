package org.openpreservation.core.model.report;

import lombok.Data;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Data
public class Report {
	private ProjectReportPart perProject;
	private List<ReleaseReportPart> perRelease;

	public Report() {
	}

	public Report(ProjectReportPart projectReportPart, Map<String, List<MilestoneReportPart>> releases) {
		this.perProject = projectReportPart;
		this.perRelease = releases.values().stream().map(ReleaseReportPart::new).collect(toList());
	}
}
