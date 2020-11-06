package br.com.dbc.data.analysis.services.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.dbc.data.analysis.models.Salesman;
import br.com.dbc.data.analysis.services.FileService;

@Component
public class FileServiceImpl implements FileService {
	private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Override
	public List<String> loadFile(String filePath) throws IOException
	{
		List<String> list = new ArrayList<String>();
		
		String lines = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.ISO_8859_1);
		list = new ArrayList<String>(Arrays.asList(lines.split("\n")));
		
		return list;
	}
	
	@Override
	public boolean generateOutputFile(String output, long customersQuantity, long salesmanQuantity, long mostExpensiveSaleId, Salesman wrostSalesman) {
		try {
			String filePath = output.concat(new SimpleDateFormat("ddMMyyyy HHmmssSSS").format(new Date())).concat(".done.dat");
			logger.info("Generating output file: ".concat(filePath));
			
			Files.createDirectories(Paths.get(output));
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		    writer.write("Customers Quantity: " + customersQuantity);
		    writer.newLine();
		    writer.write("Salesmans Quantity: " + salesmanQuantity);
		    writer.newLine();
		    writer.write("Most Expensive Sale ID: " + mostExpensiveSaleId);
		    writer.newLine();
		    writer.write("Wrost Salesman: " + wrostSalesman);
		    
		    writer.close();
		    
		    logger.info("Output file successfully generated in path: ".concat(filePath));
			
			
			return true;	
		}
		catch (IOException ex) {
			logger.error("Error when trying to generate output files.");
			ex.printStackTrace();
		}
		
		return false;
	}
}
