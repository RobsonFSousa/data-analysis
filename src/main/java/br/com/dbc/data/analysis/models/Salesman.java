package br.com.dbc.data.analysis.models;

import java.math.BigDecimal;

public class Salesman extends Entity {
	
    private String cpf;
	private String name;
	private BigDecimal salary;
	
	// Constructor
	public Salesman(String cpf, String name, BigDecimal salary) {
		this.cpf = cpf;
		this.name = name;
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Salesman [cpf=" + cpf + ", name=" + name + ", salary=" + salary + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Salesman other = (Salesman) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	// Getters and Setters
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

}
