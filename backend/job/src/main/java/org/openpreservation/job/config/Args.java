package org.openpreservation.job.config;

import com.beust.jcommander.Parameter;
import lombok.Data;

@Data
public class Args {
    @Parameter(names = {"-c", "-config"}, description = "config file path", required = true)
    private String config;

    @Parameter(names = {"-r", "-root"}, description = "root report dictionary path", required = true)
    private String root;
}
