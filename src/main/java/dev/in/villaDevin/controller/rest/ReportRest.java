package dev.in.villaDevin.controller.rest;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.in.villaDevin.controller.service.ReportService;
import dev.in.villaDevin.model.transport.ReportResponseDTO;

@RestController
@RequestMapping("/report")
public class ReportRest {

	private ReportService reportService;

	public ReportRest(ReportService reportService) {
		this.reportService = reportService;
	}

	@GetMapping()
	public ReportResponseDTO villageReport() throws SQLException {
		return reportService.reportGenerator();
	}

}
