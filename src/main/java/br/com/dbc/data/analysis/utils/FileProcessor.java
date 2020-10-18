package br.com.dbc.data.analysis.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.models.ProcessedFileSummary;
import br.com.dbc.data.analysis.models.Report;
import br.com.dbc.data.analysis.models.Sale;
import br.com.dbc.data.analysis.models.Salesman;
import br.com.dbc.data.analysis.factories.AbstractFactory;
import br.com.dbc.data.analysis.factories.CustomerFactory;
import br.com.dbc.data.analysis.factories.SaleFactory;
import br.com.dbc.data.analysis.factories.SalesmanFactory;
import br.com.dbc.data.analysis.models.Customer;

@Component
public class FileProcessor {
	@Autowired
	FileUtil fileUtil;
	
	List<FileDBC> filesDBC;
	
	private final String INPUT_FILES_PATH = System.getProperty("user.home")
			.concat(File.separator)
			.concat("data")
			.concat(File.separator)
			.concat("in")
			.concat(File.separator);
	private final String OUTPUT_FILES_PATH = System.getProperty("user.home")
			.concat(File.separator)
			.concat("data")
			.concat(File.separator)
			.concat("out")
			.concat(File.separator);
	
	/**
	 * Process the input files.
	 * @return
	 */
	public List<FileDBC> ProcessFilesDBCFromDirectory() {
		filesDBC = new ArrayList<FileDBC>();
		
		Map<String, List<String>> files = fileUtil.loadAllFiles(INPUT_FILES_PATH);
		
		files.forEach((filePath, lines) -> {
			FileDBC fileDBC = new FileDBC();
			fileDBC.setPath(filePath);
			
			List<Salesman> salesmans = new ArrayList<Salesman>();
			List<Customer> customers = new ArrayList<Customer>();
			List<Sale> sales = new ArrayList<Sale>();
					
			for (String line : lines) {
				AbstractFactory abstractFactory;
				String[] splitted = line.replaceAll("(\\r\\n|\\n|\\r)", "").split("ç");
				switch (splitted[0]) {
					// Salesman
					case "001":
						abstractFactory = new SalesmanFactory();
						Salesman salesman = (Salesman) abstractFactory.createInstance(splitted);
						salesmans.add(salesman);
						
						break;
						
					// Customer
					case "002":
						abstractFactory = new CustomerFactory();
						Customer customer = (Customer) abstractFactory.createInstance(splitted);
						customers.add(customer);
						
						break;
						
					// Sale
					case "003":
						abstractFactory = new SaleFactory();
						Sale sale = (Sale) abstractFactory.createInstance(splitted);
						sales.add(sale);
						break;
	
					default:
						throw new InvalidParameterException("Invalid line: ".concat(line));
				}
			}
			
			fileDBC.setSalesmans(salesmans);
			fileDBC.setCustomers(customers);
			fileDBC.setSales(sales);
			
			try {
				GenerateOutputFile(OUTPUT_FILES_PATH, fileDBC);
				filesDBC.add(fileDBC);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});
		
		
		return filesDBC;
	}
	
	/**
	 * Generate the output files.
	 * @param fileDBC
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public boolean GenerateOutputFile(String output, FileDBC fileDBC) throws IOException {
		Files.createDirectories(Paths.get(output));
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(output.concat(new SimpleDateFormat("ddMMyyyy HHmmssSSS").format(new Date())).concat(".done.dat")));
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
		
		return true;
	}
	
	/**
	 * Generate the report with the summary of the processed files.
	 * @return
	 */
	public Report GenerateReport() {
		if (filesDBC != null && filesDBC.size() > 0) {
			
			List<ProcessedFileSummary> processedFilesSummary = new ArrayList<ProcessedFileSummary>();
			
			for (FileDBC fileDBC : filesDBC) {
				ProcessedFileSummary processedFileSummary = new ProcessedFileSummary(fileDBC.getPath(),
						fileDBC.getCustomers().size(),
						fileDBC.getSalesmans().size(),
						fileDBC.GetMostExpensiveSale().getId(), 
						fileDBC.GetWorstSalesman());
				
				processedFilesSummary.add(processedFileSummary);
			}
			
			return new Report(processedFilesSummary);
		}
		
		return null;
	}

}
