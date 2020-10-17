package br.com.dbc.data.analysis.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
		
		Map<String, List<Sale>> result = sales.stream().collect(Collectors.groupingBy(Sale::GetSalesmanName));
		System.out.println(result);
		
		List<Sale> clonedSales = new ArrayList<Sale>(sales);
		clonedSales.sort(Comparator.comparingDouble(Sale::GetTotal));
		
		return clonedSales.get(0).getSalesman();
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
