package org.openpreservation.api.api;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.openpreservation.api.api.handler.RestExceptionHandler;
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
	@ApiOperation("Returns a list of report summaries for all latest created reports")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved report summary"),
	})
	public List<ReportSummary> getLastReports() {
		return reportService.getLastReports();
	}

	@GetMapping(value = "/organisations/{organisation}/repositories/{repo}/reports")
	@ApiOperation("Returns report summary for organization's repository")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved report summary"),
			@ApiResponse(code = 404, message = "Organization or organization's repository does not exists", response = RestExceptionHandler.GenericResponse.class)
	})
	public ReportSummary getReports(@PathVariable(value = "organisation") String org,
	                                @PathVariable(value = "repo") String repo) {
		return reportService.getReports(org, repo);
	}

	@GetMapping(value = "/organisations/{organisation}/repositories/{repo}/reports/{reportDate}")
	@ApiOperation("Returns report for organization's repository")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved report"),
			@ApiResponse(code = 404, message = "Organization/organization's repository/Report with current date does not exists", response = RestExceptionHandler.GenericResponse.class)
	})
	public Report getReport(@PathVariable(value = "organisation") String org,
	                        @PathVariable(value = "repo") String repo,
	                        @PathVariable(value = "reportDate")
	                        @ApiParam(required = true, value = "yyyy-MM-dd pattern date", example = "2019-12-30")
	                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reportDate) {
		return reportService.getReport(org, repo, reportDate);
	}
}
