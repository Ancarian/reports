package org.openpreservation.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReportSummary {
	private String organisation;
	private List<RepositorySummary> repositorySummaries;

	public ReportSummary(String organisation, List<RepositorySummary> repositorySummaries) {
		this.organisation = organisation;
		this.repositorySummaries = repositorySummaries;
	}
}
