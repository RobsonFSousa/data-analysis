package br.com.dbc.data.analysis.models;

import java.math.BigDecimal;
import java.util.List;

public class Sale extends Entity {

    private long id;
	private List<SaleItem> saleItens;
	private Salesman salesman;
	
	/** 
	* Class constructor.
	*/
	public Sale(long id, List<SaleItem> saleItens, Salesman salesman) {
		this.id = id;
		this.saleItens = saleItens;
		this.salesman = salesman;
	}

	@Override
	public String toString() {
		return "Sale [id=" + id + ", saleItens=" + saleItens.toString() + ", salesman=" + salesman.toString() + "]";
	}

	/**
	 * Calculate the sale total. 
	 * @return
	 */
	public Double getTotal() {
		BigDecimal total = new BigDecimal(0);
		
		for (SaleItem saleItem : saleItens) {
			total = total.add(saleItem.GetTotal());
		}
		
		return total.doubleValue();
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
