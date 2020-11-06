package br.com.dbc.data.analysis.factories;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

import br.com.dbc.data.analysis.models.Salesman;

@Component
public class SalesmanFactory {
	
	private final int SALESMAN_CPF = 1, SALESMAN_NAME = 2, SALESMAN_SALARY = 3;

	public Salesman createInstance(String[] splitted) {
		return new Salesman(splitted[SALESMAN_CPF], splitted[SALESMAN_NAME], new BigDecimal(splitted[SALESMAN_SALARY]));
	}

}
