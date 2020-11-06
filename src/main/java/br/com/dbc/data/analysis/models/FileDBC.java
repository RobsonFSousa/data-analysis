package br.com.dbc.data.analysis.models;

import java.util.List;

public class FileDBC {
	
	private String path;
	private List<Salesman> salesmans;
	private List<Customer> customers;
	private List<Sale> sales;

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
