package br.com.dbc.data.analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbc.data.analysis.models.Report;
import br.com.dbc.data.analysis.utils.FileProcessor;

@SpringBootApplication
@RestController
public class Application {
	@Autowired
	FileProcessor fileProcessor;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@GetMapping("/process-files")
	public Report hello() {
		fileProcessor.ProcessFilesDBCFromDirectory();
		return fileProcessor.GenerateReport();
	}

}
