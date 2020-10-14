package br.com.dbc.data.analysis;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {
	@Autowired
	ResourceLoader resourceLoader;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		
		try {
			String lines = new String(Files.readAllBytes(Paths.get(System.getProperty("user.home") + File.separator + "data" + File.separator + "in" + File.separator + "sale.dat")), StandardCharsets.ISO_8859_1);

			System.out.println(lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return String.format("Hello %s!", name);
	}

}
