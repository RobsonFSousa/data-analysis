package br.com.dbc.data.analysis;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbc.data.analysis.utils.FileUtil;

@SpringBootApplication
@RestController
public class Application {
	@Autowired
	FileUtil fileUtil;
	

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		
		fileUtil.loadAllFiles(System.getProperty("user.home") + File.separator + "data" + File.separator + "in" + File.separator);
		
		return String.format("Hello %s!", name);
	}

}
