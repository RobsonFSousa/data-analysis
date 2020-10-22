package br.com.dbc.data.analysis.services.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.models.Sale;
import br.com.dbc.data.analysis.models.Salesman;
import br.com.dbc.data.analysis.services.FileProcessorService;
import br.com.dbc.data.analysis.factories.AbstractFactory;
import br.com.dbc.data.analysis.factories.CustomerFactory;
import br.com.dbc.data.analysis.factories.SaleFactory;
import br.com.dbc.data.analysis.factories.SalesmanFactory;
import br.com.dbc.data.analysis.models.Customer;

@Service
public class FileProcessorServiceImpl implements FileProcessorService {
	private static Logger logger = LoggerFactory.getLogger(FileProcessorServiceImpl.class);
	
	/**
	 * 
	 */
	public FileDBC processDBCFile(String filePath, List<String> lines) {
		FileDBC fileDBC = new FileDBC();
		
		logger.info("Initializing batch processing from path: {}", filePath);
		
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
					logger.error("Error when trying to read file: {}", filePath);
					logger.error("Error in line: {}", line);
					
					throw new InvalidParameterException("Invalid line: ".concat(line));
			}
		}
		
		fileDBC.setSalesmans(salesmans);
		fileDBC.setCustomers(customers);
		fileDBC.setSales(sales);
		
		logger.info("File successfully processed: {}", filePath);
		
		return fileDBC;
	}

}
