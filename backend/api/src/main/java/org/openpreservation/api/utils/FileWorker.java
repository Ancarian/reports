package org.openpreservation.api.utils;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class FileWorker {
	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static List<String> getFileNames(Path rootPath) {
		try {
			return Files.walk(rootPath)
					.filter(path -> path.toFile().isFile())
					.map(path -> FilenameUtils.removeExtension(path.toFile().getName()))
					.sorted((o1, o2) -> LocalDate.parse(o2, formatter)
							.compareTo(LocalDate.parse(o1, formatter)))
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
