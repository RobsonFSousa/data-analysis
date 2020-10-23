package br.com.dbc.data.analysis.models;

public class Report {
	
	private long customersQuantity;
	private long salesmansQuantity;
	private long mostExpensiveSaleId;
	private Salesman wrostSalesman;
	
	
	/**
	 * Constructor.
	 * @param customersQuantity
	 * @param salesmansQuantity
	 * @param mostExpensiveSaleId
	 * @param wrostSalesman
	 */
	public Report(long customersQuantity, long salesmansQuantity, long mostExpensiveSaleId, Salesman wrostSalesman) {
		this.customersQuantity = customersQuantity;
		this.salesmansQuantity = salesmansQuantity;
		this.mostExpensiveSaleId = mostExpensiveSaleId;
		this.wrostSalesman = wrostSalesman;
	}
	
	@Override
	public String toString() {
		return "Report [customersQuantity=" + customersQuantity + ", salesmansQuantity=" + salesmansQuantity
				+ ", mostExpensiveSaleId=" + mostExpensiveSaleId + ", wrostSalesman=" + wrostSalesman.toString() + "]";
	}

	// Getters and Setters
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
