package br.com.dbc.data.analysis.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbc.data.analysis.models.Report;
import br.com.dbc.data.analysis.services.FileProcessorService;
import br.com.dbc.data.analysis.utils.Consts;

@RestController
public class FileProcessorController {
	@Autowired
	FileProcessorService fileProcessorService;
	
	/**
	 * Mapping for process files.
	 * @return
	 * @throws IOException 
	 */
	@GetMapping("/process-files")
	public Report hello() throws IOException {
		fileProcessorService.ProcessFilesDBCFromDirectory(Consts.INPUT_FILES_PATH);
		return fileProcessorService.GenerateReport();
	}
}
