package br.com.dbc.data.analysis.factories;

import br.com.dbc.data.analysis.models.Customer;

public class CustomerFactory {
	private final int CUSTOMER_CNPJ = 1,
			          CUSTOMER_NAME = 2,
			          CUSTOMER_BUSINESS_AREA = 3;
	
	public Customer createInstance(String[] splitted) {
		return new Customer(splitted[CUSTOMER_CNPJ], splitted[CUSTOMER_NAME], splitted[CUSTOMER_BUSINESS_AREA]);
	}
}
