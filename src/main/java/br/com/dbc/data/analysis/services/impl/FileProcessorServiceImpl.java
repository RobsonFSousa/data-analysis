package br.com.dbc.data.analysis.services.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
	
	@Override
	public Salesman getWorstSalesman(FileDBC fileDbc) {
		Map<String, BigDecimal> totalSales = new HashMap<String, BigDecimal>();
		Map<String, List<Sale>> result = fileDbc.getSales().stream().collect(Collectors.groupingBy(Sale::GetSalesmanName));
		
		result.forEach((salesmanName, sales) -> {
			BigDecimal total = new BigDecimal(0);
			
			for (Sale sale : sales) {
				total = total.add(new BigDecimal(sale.getTotal()));
			}
			totalSales.put(salesmanName, total);
		}); 
		
		Map.Entry<String, BigDecimal> worstSalesmanEntry = getWorstSalesman(totalSales);
		Optional<Salesman> worstSalesman = fileDbc.getSalesmans().stream().filter(s -> s.getName().equals(worstSalesmanEntry.getKey())).findFirst();
		
		return worstSalesman.get();
	}


	@Override
	public Sale getMostExpensiveSale(FileDBC fileDbc) {
		List<Sale> clonedSales = new ArrayList<Sale>(fileDbc.getSales());
		clonedSales.sort(Comparator.comparingDouble(Sale::getTotal).reversed());
		
		return clonedSales.get(0);
	}
	
	private static Map.Entry<String, BigDecimal> getWorstSalesman(Map<String, BigDecimal> hasmap){
		List<Map.Entry<String, BigDecimal> > list = 
	               new LinkedList<Map.Entry<String, BigDecimal> >(hasmap.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<String, BigDecimal>>() {
			public int compare(Map.Entry<String, BigDecimal> o1,
							   Map.Entry<String, BigDecimal> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
		
        return list.get(0);
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
