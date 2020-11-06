package br.com.dbc.data.analysis.services.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.models.Sale;
import br.com.dbc.data.analysis.models.Salesman;
import br.com.dbc.data.analysis.services.FileProcessorService;
import br.com.dbc.data.analysis.factories.CustomerFactory;
import br.com.dbc.data.analysis.factories.SaleFactory;
import br.com.dbc.data.analysis.factories.SalesmanFactory;
import br.com.dbc.data.analysis.models.Customer;

@Service
public class FileProcessorServiceImpl implements FileProcessorService {
	private static Logger logger = LoggerFactory.getLogger(FileProcessorServiceImpl.class);
	
	@Autowired
	SalesmanFactory entityFactory;
	
	@Override
	public FileDBC processDBCFile(String filePath, List<String> lines) {
		FileDBC fileDBC = new FileDBC();
		
		logger.info("Initializing batch processing from path: {}", filePath);
		
		List<Salesman> salesmans = new ArrayList<Salesman>();
		List<Customer> customers = new ArrayList<Customer>();
		List<Sale> sales = new ArrayList<Sale>();
				
		for (String line : lines) {
			logger.info("Processing line: {}", line);
			String[] splitted = line.replaceAll("(\\r\\n|\\n|\\r)", "").split("ç");
			
			switch (splitted[0]) {
				case "001":
					addSalesman(salesmans, splitted);
					break;
				case "002":
					addCustomer(customers, splitted);
					break;
				case "003":
					addSale(sales, splitted);
					break;
				default:
					logger.info("Error when trying to read line: {}", line);
					
					throw new InvalidParameterException("Invalid line: ".concat(line));
				}
		}
		
		fileDBC.setSalesmans(salesmans);
		fileDBC.setCustomers(customers);
		fileDBC.setSales(sales);
		
		logger.info("File successfully processed: {}", filePath);
		
		return fileDBC;
	}
	
	private boolean addSalesman(List<Salesman> salesmans, String[] splitted) {
		SalesmanFactory salesmanFactory = new SalesmanFactory();
		Salesman salesman = salesmanFactory.createInstance(splitted);
		salesmans.add(salesman);
		logger.info(salesman.toString());
		return true;
	}
	
	private boolean addCustomer(List<Customer> customers, String[] splitted) {
		CustomerFactory customerFactory = new CustomerFactory();
		Customer customer = customerFactory.createInstance(splitted);
		customers.add(customer);
		logger.info(customer.toString());
		return true;
	}
	
	private boolean addSale(List<Sale> sales, String[] splitted) {
		SaleFactory saleFactory = new SaleFactory();
		Sale sale = saleFactory.createInstance(splitted);
		sales.add(sale);
		logger.info(sale.toString());
		return true;
	}

}
