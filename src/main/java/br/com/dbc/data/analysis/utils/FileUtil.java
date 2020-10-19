package br.com.dbc.data.analysis.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.dbc.data.analysis.models.FileDBC;

@Component
public class FileUtil {
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * Load all .bat files from directory.
	 * @param directory
	 * @return
	 */
	public Map<String, List<String>> loadAllFiles(String directory)
	{
		Map<String, List<String>> filesMap = new HashMap<String, List<String>>();
		
		try {
			File path = new File(directory);
			// Filter for .dat only
			File files[] = path.listFiles((dir, name) -> name.toLowerCase().endsWith(".dat"));

			for(File file : files) {
				String filePath = file.getAbsolutePath();
				String lines = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.ISO_8859_1);
				List<String> list = new ArrayList<String>(Arrays.asList(lines.split("\n")));
				
				filesMap.put(filePath, list);
	      }

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return filesMap;
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
		    
		    logger.info("Output file successfully generated in path: ".concat(fileDBC.getPath()));
			
			return true;	
		}
		catch (IOException ex) {
			logger.error("Error when trying to read file: ".concat(fileDBC.getPath()));
		}
		
		return false;
	}
}
