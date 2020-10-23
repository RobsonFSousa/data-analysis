package br.com.dbc.data.analysis.services.impl;

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
import br.com.dbc.data.analysis.factories.EntityFactory;
import br.com.dbc.data.analysis.models.Customer;
import br.com.dbc.data.analysis.models.Entity;

@Service
public class FileProcessorServiceImpl implements FileProcessorService {
	private static Logger logger = LoggerFactory.getLogger(FileProcessorServiceImpl.class);
	
	@Autowired
	EntityFactory entityFactory;
	
	@Override
	public FileDBC processDBCFile(String filePath, List<String> lines) {
		FileDBC fileDBC = new FileDBC();
		
		logger.info("Initializing batch processing from path: {}", filePath);
		
		List<Salesman> salesmans = new ArrayList<Salesman>();
		List<Customer> customers = new ArrayList<Customer>();
		List<Sale> sales = new ArrayList<Sale>();
				
		for (String line : lines) {
			logger.info("Processing line: {}", line);
			Entity entity = entityFactory.createInstance(line);
			if (entity instanceof Salesman)
				salesmans.add((Salesman) entity);
			else if (entity instanceof Customer)
				customers.add((Customer) entity);
			else if (entity instanceof Sale)
				sales.add((Sale) entity);
		}
		
		fileDBC.setSalesmans(salesmans);
		fileDBC.setCustomers(customers);
		fileDBC.setSales(sales);
		
		logger.info("File successfully processed: {}", filePath);
		
		return fileDBC;
	}

}
