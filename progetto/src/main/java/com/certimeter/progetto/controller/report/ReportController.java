package com.certimeter.progetto.controller.report;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certimeter.progetto.model.Report;

@RestController
@RequestMapping("/reports")
public class ReportController implements ReportControllerInterface {

	@Override
	@GetMapping("{path}/list")
	public List<Report> getList(@PathVariable String path) {
		return null;
	}

	@Override
	@GetMapping("{path}/{reportId}")
	public Report getReport(@PathVariable String reportId) {
		return null;
	}

	@Override
	@PostMapping("{path}/")
	public Report createReport(@RequestBody Report report) {
		return null;
	}

	@Override
	@PutMapping("{path}/")
	public Report updateReport(@RequestBody Report report) {
		return null;
	}

	@Override
	@DeleteMapping("{path}/{reportId}")
	public void deleteReport(@PathVariable String reportId) {
	}
}
