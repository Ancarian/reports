package org.openpreservation.core.model.config;

import lombok.Data;

import java.util.List;

@Data
public class Organisation {
	private String organisation;
	private List<String> projects;

	public Organisation() {
	}
}
