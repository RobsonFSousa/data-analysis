package br.com.dbc.data.analysis.models;

public class SaleItem {
	
	private long id;
	
	private double quantity;
	
	private double price;

	// Methods
	public Double GetTotal() {
		return quantity * price;
	}
	
	//Getters and Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
