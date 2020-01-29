package org.openpreservation.api.dto;

import lombok.Data;
import org.openpreservation.core.model.report.Report;

import java.util.List;

@Data
public class RepositorySummary {
	private String repository;
	private Report report;

	public RepositorySummary(String repository, Report report) {
		this.repository = repository;
		this.report = report;
	}
}
