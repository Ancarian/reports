package org.openpreservation.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class RepositorySummary {
	private String repository;
	private List<String> filenames;

	public RepositorySummary(String repository, List<String> filenames) {
		this.repository = repository;
		this.filenames = filenames;
	}
}
