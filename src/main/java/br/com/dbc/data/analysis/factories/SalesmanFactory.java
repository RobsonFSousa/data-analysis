package br.com.dbc.data.analysis.factories;

import java.math.BigDecimal;

import br.com.dbc.data.analysis.models.Salesman;

public class SalesmanFactory implements AbstractFactory {

	@Override
	public Object createInstance(String[] lineSplitted) {
		return new Salesman(lineSplitted[1], lineSplitted[2], new BigDecimal(lineSplitted[3]));
	}

}
