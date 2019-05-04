package org.openpreservation.core.model.report;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.eclipse.egit.github.core.Milestone;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class MilestoneReportPart {
    @JsonIgnore
    private final Pattern pattern = Pattern.compile("((\\d+\\.?)+\\d+)-(m[1-4])");

    private int number;
    private String url;
    private String title;
    private Date dueOn;
    private Date createdAt;

    private String version;
    private String shortVersionType;
    private String fullVersion;
    private List<CustomIssue> issues;

    private boolean isReleaseMilestone;

    public MilestoneReportPart() {
    }

    public MilestoneReportPart(Milestone milestone) {
        this.number = milestone.getNumber();
        this.url = milestone.getUrl();
        this.title = milestone.getTitle();
        this.dueOn = milestone.getDueOn();
        this.createdAt = milestone.getCreatedAt();

        Matcher m = pattern.matcher(this.title);
        if (m.find()) {
            this.fullVersion = m.group(0);
            this.version = m.group(1);
            this.shortVersionType = m.group(3);
            this.isReleaseMilestone = true;
        }
    }
}
