package br.com.dbc.data.analysis.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.models.Sale;
import br.com.dbc.data.analysis.models.SaleItem;
import br.com.dbc.data.analysis.models.Salesman;
import br.com.dbc.data.analysis.models.Customer;

@Component
public class FileProcessor {
	@Autowired
	FileUtil fileUtil;
	
	private final String FILES_PATH = System.getProperty("user.home") + File.separator + "data" + File.separator +	"in" + File.separator;
	
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
				String[] splitted = line.replaceAll("(\\r\\n|\\n|\\r)", "").split("ç");
				switch (splitted[0]) {
					// Salesman
					case "001":
						Salesman salesman = GetSalesmanFromLine(splitted);
						salesmans.add(salesman);
						
						break;
						
					// Customer
					case "002":
						Customer customer = GetCustomerFromLine(splitted);
						customers.add(customer);
						
						break;
						
					// Sale
					case "003":
						Sale sale = GetSaleFromLine(splitted);
						sales.add(sale);
						break;
	
					default:
						break;
				}
			}
			
			fileDBC.setSalesmans(salesmans);
			fileDBC.setCustomers(customers);
			fileDBC.setSales(sales);
			
			filesDBC.add(fileDBC);
		});
		
		
		return filesDBC;
	}
	
	private Salesman GetSalesmanFromLine(String[] lineSplitted) {
		Salesman salesman = new Salesman();
		
		salesman.setCpf(lineSplitted[1]);
		salesman.setName(lineSplitted[2]);
		salesman.setSalary(new Double(lineSplitted[3]));
		
		return salesman;
	}
	
	private Customer GetCustomerFromLine(String[] lineSplitted) {
		Customer customer = new Customer();
		
		customer.setCnpj(lineSplitted[1]);
		customer.setName(lineSplitted[2]);
		customer.setBusinessArea(lineSplitted[3]);
		
		return customer;
	}
	
	private Sale GetSaleFromLine(String[] lineSplitted) {
		Sale sale = new Sale();
		Salesman salesman = new Salesman();
		salesman.setName(lineSplitted[3]);
		
		sale.setId(Long.parseLong(lineSplitted[1]));
		sale.setSaleItens(GetSaleItensFromLine(lineSplitted[2].replace("[", "").replace("]", "").split(",")));
		sale.setSalesman(salesman);
		
		return sale;
	}
	
	private List<SaleItem> GetSaleItensFromLine(String[] lineSplitted) {
		List<SaleItem> saleItens = new ArrayList<SaleItem>();
		
		for (String line : lineSplitted) {
			SaleItem saleItem = new SaleItem();
			
			String[] splittedItem = line.split("-");
			
			saleItem.setId(Long.parseLong(splittedItem[0]));
			saleItem.setPrice(Double.parseDouble(splittedItem[1]));
			saleItem.setQuantity(Double.parseDouble(splittedItem[2]));
			
			saleItens.add(saleItem);
		}
		
		return saleItens;
	}

}
