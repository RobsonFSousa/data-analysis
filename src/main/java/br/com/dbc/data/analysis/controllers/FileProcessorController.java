package br.com.dbc.data.analysis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbc.data.analysis.models.Report;
import br.com.dbc.data.analysis.utils.FileProcessor;

@RestController
public class FileProcessorController {
	@Autowired
	FileProcessor fileProcessor;
	
	/**
	 * Mapping for process files.
	 * @return
	 */
	@GetMapping("/process-files")
	public Report hello() {
		fileProcessor.ProcessFilesDBCFromDirectory();
		return fileProcessor.GenerateReport();
	}
}
