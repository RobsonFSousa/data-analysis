package br.com.dbc.data.analysis.models;

public class ProcessedFileSummary {
	
	private String filePath;
	private long customersQuantity;
	private long salesmansQuantity;
	private long mostExpensiveSaleId;
	private Salesman wrostSalesman;
	
	// Constructor
	public ProcessedFileSummary(String filePath, long customersQuantity, long salesmansQuantity, long mostExpensiveSaleId,
			Salesman wrostSalesman) {
		this.filePath = filePath;
		this.customersQuantity = customersQuantity;
		this.salesmansQuantity = salesmansQuantity;
		this.mostExpensiveSaleId = mostExpensiveSaleId;
		this.wrostSalesman = wrostSalesman;
	}
	
	// Getters and Setters
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public long getCustomersQuantity() {
		return customersQuantity;
	}
	public void setCustomersQuantity(long customersQuantity) {
		this.customersQuantity = customersQuantity;
	}
	public long getSalesmansQuantity() {
		return salesmansQuantity;
	}
	public void setSalesmansQuantity(long salesmansQuantity) {
		this.salesmansQuantity = salesmansQuantity;
	}
	public long getMostExpensiveSaleId() {
		return mostExpensiveSaleId;
	}
	public void setMostExpensiveSaleId(long mostExpensiveSaleId) {
		this.mostExpensiveSaleId = mostExpensiveSaleId;
	}
	public Salesman getWrostSalesman() {
		return wrostSalesman;
	}
	public void setWrostSalesman(Salesman wrostSalesman) {
		this.wrostSalesman = wrostSalesman;
	}
}
