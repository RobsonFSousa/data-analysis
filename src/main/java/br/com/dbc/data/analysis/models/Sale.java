package br.com.dbc.data.analysis.models;

import java.util.List;

public class Sale {

    private long id;
	
	private List<SaleItem> saleItens;
	
	private Salesman salesman; 

	// Methods
	public Double GetTotal() {
		Double total = 0.0;
		
		for (SaleItem saleItem : saleItens) {
			total += saleItem.GetTotal();
		}
		
		return total;
	}
	
	// Getters and Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<SaleItem> getSaleItens() {
		return saleItens;
	}

	public void setSaleItens(List<SaleItem> saleItens) {
		this.saleItens = saleItens;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public void setSalesman(Salesman salesman) {
		this.salesman = salesman;
	}
	
	public String GetSalesmanName() {
		return salesman.getName();
	}
}
