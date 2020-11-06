package br.com.dbc.data.analysis.factories;

import java.math.BigDecimal;

import br.com.dbc.data.analysis.models.SaleItem;

public class SaleItemFactory {
	private final int SALE_ITEM_ID = 0, SALE_ITEM_QUANTITY = 1, SALE_ITEM_PRICE = 2;
	
	public SaleItem createInstance(String[] splitted) {
		return new SaleItem(Long.parseLong(splitted[SALE_ITEM_ID]), new BigDecimal(splitted[SALE_ITEM_QUANTITY]), new BigDecimal(splitted[SALE_ITEM_PRICE]));
	}
}
