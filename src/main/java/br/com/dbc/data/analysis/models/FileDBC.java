package br.com.dbc.data.analysis.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileDBC {
	
	private long id;
	
	private String path;
	
	List<Salesman> salesmans;
	
	List<Customer> customers;
	
	List<Sale> sales;
	
	// Methods
	public Sale GetMostExpensiveSale() {
		List<Sale> clonedSales = new ArrayList<Sale>(sales);
		clonedSales.sort(Comparator.comparingDouble(Sale::GetTotal).reversed());
		
		return clonedSales.get(0);
	}
	
	public Salesman GetWorstSalesman() {
		Map<String, Double> totalSales = new HashMap<String, Double>();
		Map<String, List<Sale>> result = sales.stream().collect(Collectors.groupingBy(Sale::GetSalesmanName));
		
		result.forEach((salesmanName, sales) -> {
			Double total = 0.0;
			
			for (Sale sale : sales) {
				total += sale.GetTotal();
			}
			totalSales.put(salesmanName, total);
		}); 
		
		Map.Entry<String, Double> worstSalesmanEntry = GetWorstSalesman(totalSales);
		Optional<Salesman> worstSalesman = salesmans.stream().filter(s -> s.getName().equals(worstSalesmanEntry.getKey())).findFirst();
		
		return worstSalesman.get();
	}
	
	public static Map.Entry<String, Double> GetWorstSalesman(Map<String, Double> hasmap){
		List<Map.Entry<String, Double> > list = 
	               new LinkedList<Map.Entry<String, Double> >(hasmap.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1,
							   Map.Entry<String, Double> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
		
        return list.get(0);
	}

	// Getters and Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<Salesman> getSalesmans() {
		return salesmans;
	}

	public void setSalesmans(List<Salesman> salesmans) {
		this.salesmans = salesmans;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
}
