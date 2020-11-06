package br.com.dbc.data.analysis.models;

import java.math.BigDecimal;

public class SaleItem {
	
	private long id;
	private BigDecimal quantity;
	private BigDecimal price;
	
	public SaleItem(long id, BigDecimal quantity, BigDecimal price) {
		this.id = id;
		this.quantity = quantity;
		this.price = price;
	}

	@Override
	public String toString() {
		return "SaleItem [id=" + id + ", quantity=" + quantity + ", price=" + price + "]";
	}

	public BigDecimal GetTotal() {
		return quantity.multiply(price);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
