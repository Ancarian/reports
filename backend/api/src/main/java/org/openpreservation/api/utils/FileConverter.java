package org.openpreservation.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openpreservation.core.model.report.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FileConverter implements Converter<Report, File> {
	private final ObjectMapper mapper;

	@Autowired
	public FileConverter(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public Report convert(File value) {
		try {
			return mapper.readValue(value, Report.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
