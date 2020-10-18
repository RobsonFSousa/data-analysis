package br.com.dbc.data.analysis.factories;

import br.com.dbc.data.analysis.models.Customer;

public class CustomerFactory implements AbstractFactory {
	private final int CNPJ = 1;
	private final int NAME = 2;
	private final int BUSINESS_AREA = 3;

	@Override
	public Object createInstance(String[] lineSplitted) {
		return new Customer(lineSplitted[CNPJ], lineSplitted[NAME], lineSplitted[BUSINESS_AREA]);
	}

}
