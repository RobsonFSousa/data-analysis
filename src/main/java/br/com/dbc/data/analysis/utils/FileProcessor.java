package br.com.dbc.data.analysis.utils;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.dbc.data.analysis.models.Sale;
import br.com.dbc.data.analysis.models.Salesman;

@Component
public class FileProcessor {
	@Autowired
	FileUtil fileUtil;
	
	private final String FILES_PATH = System.getProperty("user.home") + File.separator + "data" + File.separator +	"in" + File.separator;
	
	public Sale GetSalesFromFiles() {
		Sale sale = new Sale();
		
		Map<String, List<String>> files = fileUtil.loadAllFiles(FILES_PATH);
		
		files.forEach((filePath, lines) -> {
			for (String line : lines) {
				String[] splitted = line.split("ç");
				switch (splitted[0]) {
					// Salesman
					case "001":
						Salesman salesman = GetSalesmanFromLine(splitted);
						sale.setSalesman(salesman);
						break;
						
					// Customer
					case "002":
						/*Customer customer = GetCustomerFromLine(splitted);
						sale.set*/
						break;
						
					// Sale
					case "003":
						
						break;
	
					default:
						break;
					}
			}
		});
		
		
		return sale;
	}
	
	private Salesman GetSalesmanFromLine(String[] lineSplitted) {
		
		Salesman salesman = new Salesman();
		salesman.setCpf(lineSplitted[1]);
		salesman.setName(lineSplitted[2]);
		salesman.setSalary(new Double(lineSplitted[3]));
		
		return salesman;
	}

}
