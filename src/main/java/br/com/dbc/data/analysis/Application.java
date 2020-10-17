package br.com.dbc.data.analysis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.models.Sale;
import br.com.dbc.data.analysis.models.Salesman;
import br.com.dbc.data.analysis.utils.FileProcessor;

@SpringBootApplication
@RestController
public class Application {
	@Autowired
	FileProcessor fileProcessor;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		List<FileDBC> files = fileProcessor.GetFilesDBCFromDirectory();
		System.out.println(files);
		
		for (FileDBC fileDBC : files) {
			Sale sale = fileDBC.GetMostExpensiveSale();
			Salesman salesman = fileDBC.GetWorstSalesman();
		}
		
		return String.format("Hello %s!", name);
	}

}
