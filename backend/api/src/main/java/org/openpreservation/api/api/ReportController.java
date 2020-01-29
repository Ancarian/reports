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
}
