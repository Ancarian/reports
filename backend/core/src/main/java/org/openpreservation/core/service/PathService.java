package org.openpreservation.core.service;

import java.io.File;

public class PathService {
    private final String root;

    public PathService(String root) {
        this.root = root;
    }

    public File getProjectDirectoryPath(String organisation, String project) {
        return new File(String.format("%s/%s/%s/", root, organisation, project));
    }

    public File getReportPath(String organisation, String project, String report) {
        return new File(String.format("%s/%s/%s/%s", root, organisation, project, report));
    }
}
