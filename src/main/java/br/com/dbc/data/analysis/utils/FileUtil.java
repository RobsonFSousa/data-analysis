package br.com.dbc.data.analysis.utils;

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

import br.com.dbc.data.analysis.models.FileDBC;

@Component
public class FileUtil {
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * Load file from a path
	 * @param directory
	 * @return
	 */
	public List<String> loadFile(String filePath)
	{
		List<String> list = new ArrayList<String>();
		
		try {
			String lines = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.ISO_8859_1);
			list = new ArrayList<String>(Arrays.asList(lines.split("\n")));
			
			return list;

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Generate the output files.
	 * @param fileDBC
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public boolean GenerateOutputFile(String output, FileDBC fileDBC) {
		try {
			String filePath = output.concat(new SimpleDateFormat("ddMMyyyy HHmmssSSS").format(new Date())).concat(".done.dat");
			logger.info("Generating output file: ".concat(filePath));
			
			Files.createDirectories(Paths.get(output));
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		    writer.write("File Path: " + fileDBC.getPath());
		    writer.newLine();
		    writer.write("Customers Quantity: " + fileDBC.getCustomers().size());
		    writer.newLine();
		    writer.write("Salesmans Quantity: " + fileDBC.getSalesmans().size());
		    writer.newLine();
		    writer.write("Most Expensive Sale ID: " + fileDBC.GetMostExpensiveSale().getId());
		    writer.newLine();
		    writer.write("Wrost Salesman: " + fileDBC.GetWorstSalesman().getName());
		    
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
