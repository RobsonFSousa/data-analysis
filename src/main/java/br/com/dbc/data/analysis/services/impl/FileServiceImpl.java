package br.com.dbc.data.analysis.services.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.models.Sale;
import br.com.dbc.data.analysis.models.Salesman;
import br.com.dbc.data.analysis.services.FileService;

@Component
public class FileServiceImpl implements FileService {
	private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Override
	public List<String> loadFile(String filePath) throws IOException
	{
		List<String> list = new ArrayList<String>();
		
		String lines = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.ISO_8859_1);
		list = new ArrayList<String>(Arrays.asList(lines.split("\n")));
		
		return list;
	}
	
	@Override
	public boolean generateOutputFile(FileDBC fileDBC, String output) {
		try {
			String filePath = output.concat(new SimpleDateFormat("ddMMyyyy HHmmssSSS").format(new Date())).concat(".done.dat");
			logger.info("Generating output file: ".concat(filePath));
			
			Files.createDirectories(Paths.get(output));
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		    writer.write("Customers Quantity: " + fileDBC.getCustomers().size());
		    writer.newLine();
		    writer.write("Salesmans Quantity: " + fileDBC.getSalesmans().size());
		    writer.newLine();
		    writer.write("Most Expensive Sale ID: " + getMostExpensiveSale(fileDBC).getId());
		    writer.newLine();
		    writer.write("Wrost Salesman: " + getWorstSalesman(fileDBC).getName());
		    
		    writer.close();
		    
		    logger.info("Output file successfully generated in path: ".concat(filePath));
			
			
			return true;	
		}
		catch (IOException ex) {
			logger.error("Error when trying to generate output files.");
			ex.printStackTrace();
		}
		
		return false;
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
}
