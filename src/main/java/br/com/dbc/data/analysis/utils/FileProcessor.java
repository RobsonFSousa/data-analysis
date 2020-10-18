package br.com.dbc.data.analysis.utils;

import java.io.File;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.models.Sale;
import br.com.dbc.data.analysis.models.SaleItem;
import br.com.dbc.data.analysis.models.Salesman;
import br.com.dbc.data.analysis.factories.AbstractFactory;
import br.com.dbc.data.analysis.factories.CustomerFactory;
import br.com.dbc.data.analysis.factories.SaleFactory;
import br.com.dbc.data.analysis.factories.SaleItemFactory;
import br.com.dbc.data.analysis.factories.SalesmanFactory;
import br.com.dbc.data.analysis.models.Customer;

@Component
public class FileProcessor {
	@Autowired
	FileUtil fileUtil;
	
	private final String FILES_PATH = System.getProperty("user.home")
			.concat(File.separator)
			.concat("data")
			.concat(File.separator)
			.concat("in")
			.concat(File.separator);
	
	public List<FileDBC> GetFilesDBCFromDirectory() {
		List<FileDBC> filesDBC = new ArrayList<FileDBC>();
		
		Map<String, List<String>> files = fileUtil.loadAllFiles(FILES_PATH);
		
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
			
			filesDBC.add(fileDBC);
		});
		
		
		return filesDBC;
	}

}
