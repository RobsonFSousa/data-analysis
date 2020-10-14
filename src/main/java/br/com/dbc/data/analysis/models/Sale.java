package br.com.dbc.data.analysis.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Sale {

	@Id
	@GeneratedValue
    private long id;
	
	@OneToMany(cascade = CascadeType.ALL,
	           orphanRemoval = true)
	private List<SaleItem> saleItens;
	
	@OneToOne(cascade = CascadeType.ALL,
	           orphanRemoval = true)
	private Salesman salesman;
}
