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
		Salesman saleSalesman = createSalesman(splitted[SALE_SALESMAN]);
		List<SaleItem> saleItens = createSaleItens(splitted[SALE_ITENS].replace("[", "").replace("]", "").split(","));
		return new Sale(Long.parseLong(splitted[SALE_ID]), saleItens, saleSalesman);
	}
	
	private Salesman createSalesman(String salesmanName) {
		return new Salesman(null, salesmanName, new BigDecimal(0));
	}
	
	private List<SaleItem> createSaleItens(String[] saleItensSplitted) {
		List<SaleItem> saleItens = new ArrayList<SaleItem>();
		
		for (String saleItemLine : saleItensSplitted) {
			String[] splittedItem = saleItemLine.split("-");
			
			SaleItem saleItem = saleItemFactory.createInstance(splittedItem);
			saleItens.add(saleItem);
		}
		
		return saleItens;
	}
}
