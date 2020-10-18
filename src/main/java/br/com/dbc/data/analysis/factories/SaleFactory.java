package br.com.dbc.data.analysis.factories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.dbc.data.analysis.models.Sale;
import br.com.dbc.data.analysis.models.SaleItem;
import br.com.dbc.data.analysis.models.Salesman;

public class SaleFactory implements AbstractFactory {
	private final int ID = 1;
	private final int SALE_ITENS = 2;
	private final int SALESMAN = 3;

	@Override
	public Object createInstance(String[] lineSplitted) {
		Salesman salesman = new Salesman(null, lineSplitted[SALESMAN], new BigDecimal(0));
		
		return new Sale(Long.parseLong(lineSplitted[ID]), GetSaleItensFromLine(lineSplitted[SALE_ITENS].replace("[", "").replace("]", "").split(",")), salesman);
	}
	
	private List<SaleItem> GetSaleItensFromLine(String[] lineSplitted) {
		List<SaleItem> saleItens = new ArrayList<SaleItem>();
		
		for (String line : lineSplitted) {
			String[] splittedItem = line.split("-");
			
			AbstractFactory abstractFactory = new SaleItemFactory();
			SaleItem saleItem = (SaleItem) abstractFactory.createInstance(splittedItem);
			saleItens.add(saleItem);
		}
		
		return saleItens;
	}

}
