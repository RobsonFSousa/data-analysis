package br.com.dbc.data.analysis.factories;

import java.math.BigDecimal;

import br.com.dbc.data.analysis.models.SaleItem;

public class SaleItemFactory implements AbstractFactory {
	private final int ID = 0;
	private final int QUANTITY = 1;
	private final int PRICE = 2;

	@Override
	public Object createInstance(String[] lineSplitted) {
		return new SaleItem(Long.parseLong(lineSplitted[ID]), new BigDecimal(lineSplitted[QUANTITY]), new BigDecimal(lineSplitted[PRICE]));
	}

}
