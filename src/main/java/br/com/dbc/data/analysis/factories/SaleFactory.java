package br.com.dbc.data.analysis.factories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.dbc.data.analysis.models.Sale;
import br.com.dbc.data.analysis.models.SaleItem;
import br.com.dbc.data.analysis.models.Salesman;

public class SaleFactory {
	private final int SALE_ID = 1, SALE_ITENS = 2, SALE_SALESMAN = 3;
	private SaleItemFactory saleItemFactory = new SaleItemFactory();
	
	public Sale createInstance(String[] splitted) {
		Salesman saleSalesman = new Salesman(null, splitted[SALE_SALESMAN], new BigDecimal(0));
		
		List<SaleItem> saleItens = new ArrayList<SaleItem>();

		String[] saleItensSplitted = splitted[SALE_ITENS].replace("[", "").replace("]", "").split(",");
		for (String saleItemLine : saleItensSplitted) {
			String[] splittedItem = saleItemLine.split("-");
			
			SaleItem saleItem = saleItemFactory.createInstance(splittedItem);
			saleItens.add(saleItem);
		}
		
		Sale sale = new Sale(Long.parseLong(splitted[SALE_ID]), saleItens, saleSalesman);
		
		return sale;
	}
}
