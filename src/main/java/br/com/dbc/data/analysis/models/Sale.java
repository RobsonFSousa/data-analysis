package br.com.dbc.data.analysis.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Sale {

	@Id
    private long id;
	
	@OneToMany(cascade = CascadeType.ALL,
	           orphanRemoval = true)
	private List<SaleItem> saleItens;
	
	@OneToOne(cascade = CascadeType.ALL,
	           orphanRemoval = true)
	private Salesman salesman;

	
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
}
