package br.com.dbc.data.analysis.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {
	
	@Id
    private String cnpj;
	
	private String name;
	
	private double businessArea;

	//Getters and Setters
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(double businessArea) {
		this.businessArea = businessArea;
	}
	
}
