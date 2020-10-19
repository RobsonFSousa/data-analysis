package br.com.dbc.data.analysis.services;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.models.ProcessedFileSummary;
import br.com.dbc.data.analysis.models.Report;
import br.com.dbc.data.analysis.models.Sale;
import br.com.dbc.data.analysis.models.Salesman;
import br.com.dbc.data.analysis.utils.Consts;
import br.com.dbc.data.analysis.utils.FileUtil;
import br.com.dbc.data.analysis.factories.AbstractFactory;
import br.com.dbc.data.analysis.factories.CustomerFactory;
import br.com.dbc.data.analysis.factories.SaleFactory;
import br.com.dbc.data.analysis.factories.SalesmanFactory;
import br.com.dbc.data.analysis.models.Customer;

@Component
public class FileProcessorService {
	@Autowired
	private FileUtil fileUtil;
	private List<FileDBC> filesDBC;
	private static Logger logger = LoggerFactory.getLogger(FileProcessorService.class);
	
	
	/**
	 * Process the input files.
	 * @return
	 */
	public List<FileDBC> ProcessFilesDBCFromDirectory(String directory) throws IOException {
		logger.info("Initializing batch processing from path: ".concat(directory));
		
		filesDBC = new ArrayList<FileDBC>();
		
		Map<String, List<String>> files = fileUtil.loadAllFiles(directory);
		logger.info("Files loaded.");
		
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
						logger.error("Error when trying to read file: ".concat(fileDBC.getPath()));
						logger.error("Error in line: ".concat(line));
						
						throw new InvalidParameterException("Invalid line: ".concat(line));
				}
			}
			
			fileDBC.setSalesmans(salesmans);
			fileDBC.setCustomers(customers);
			fileDBC.setSales(sales);
			
			fileUtil.GenerateOutputFile(Consts.OUTPUT_FILES_PATH, fileDBC);
			filesDBC.add(fileDBC);
			
			logger.info("File successfully processed: ".concat(fileDBC.getPath()));
			
		});
		
		
		return filesDBC;
	}
	
	/**
	 * Generate the report with the summary of the processed files.
	 * @return
	 */
	public Report GenerateReport() {
		if (filesDBC != null && filesDBC.size() > 0) {
			logger.info("Generating Report...");
			
			List<ProcessedFileSummary> processedFilesSummary = new ArrayList<ProcessedFileSummary>();
			
			for (FileDBC fileDBC : filesDBC) {
				ProcessedFileSummary processedFileSummary = new ProcessedFileSummary(fileDBC.getPath(),
						fileDBC.getCustomers().size(),
						fileDBC.getSalesmans().size(),
						fileDBC.GetMostExpensiveSale().getId(), 
						fileDBC.GetWorstSalesman());
				
				processedFilesSummary.add(processedFileSummary);
			}
			
			logger.info("Report successfully generated.");
			return new Report(processedFilesSummary);
		}
		
		logger.info("No data to generating Report.");
		return null;
	}

}
