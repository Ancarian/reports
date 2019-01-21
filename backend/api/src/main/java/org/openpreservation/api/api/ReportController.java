package org.openpreservation.api.api;


import org.openpreservation.api.dto.ReportSummary;
import org.openpreservation.api.service.ReportService;
import org.openpreservation.core.model.report.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ReportController {
	private ReportService reportService;

	@Autowired
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}

	@GetMapping(value = "/last-reports")
	public List<ReportSummary> getLastReports() {
		return reportService.getLastReports();
	}

	@GetMapping(value = "/organisations/{organisation}/repositories/{repo}/reports")
	public ReportSummary getReports(@PathVariable(value = "organisation") String org,
									@PathVariable(value = "repo") String repo) {
		return reportService.getReports(org, repo);
	}

	@GetMapping(value = "/organisations/{organisation}/repositories/{repo}/reports/{reportDate}")
	public Report getReport(@PathVariable(value = "organisation") String org,
	                        @PathVariable(value = "repo") String repo,
	                        @PathVariable(value = "reportDate")
							@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reportDate) {
		return reportService.getReport(org, repo, reportDate);
	}
}
