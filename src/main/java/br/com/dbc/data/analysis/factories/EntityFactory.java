package br.com.dbc.data.analysis.factories;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.dbc.data.analysis.models.Customer;
import br.com.dbc.data.analysis.models.Entity;
import br.com.dbc.data.analysis.models.Sale;
import br.com.dbc.data.analysis.models.SaleItem;
import br.com.dbc.data.analysis.models.Salesman;
import br.com.dbc.data.analysis.services.impl.FileServiceImpl;

@Component
public class EntityFactory implements AbstractFactory<Entity>{
	private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	
	private final int SALE_ITEM_ID = 0;
	private final int CUSTOMER_CNPJ = 1,
					  SALESMAN_CPF = 1,
					  SALE_ITEM_QUANTITY = 1,
					  SALE_ID = 1;
	private final int CUSTOMER_NAME = 2,
			          SALESMAN_NAME = 2,
			          SALE_ITEM_PRICE = 2,
			          SALE_ITENS = 2;
	private final int CUSTOMER_BUSINESS_AREA = 3,
					  SALESMAN_SALARY = 3,
					  SALE_SALESMAN = 3;

	@Override
	public Entity createInstance(String line) {
		String[] splitted = line.replaceAll("(\\r\\n|\\n|\\r)", "").split("ç");
		
		switch (splitted[0]) {
			// Salesman
			case "001":
				Salesman salesman = new Salesman(splitted[SALESMAN_CPF], splitted[SALESMAN_NAME], new BigDecimal(splitted[SALESMAN_SALARY]));
				logger.info("{}", salesman.toString());
				
				return salesman;
				
			// Customer
			case "002":
				Customer customer = new Customer(splitted[CUSTOMER_CNPJ], splitted[CUSTOMER_NAME], splitted[CUSTOMER_BUSINESS_AREA]);
				logger.info("{}", customer.toString());
				
				return customer;
				
			// Sale
			case "003":
				Salesman saleSalesman = new Salesman(null, splitted[SALE_SALESMAN], new BigDecimal(0));
				
				List<SaleItem> saleItens = new ArrayList<SaleItem>();
	
				String[] saleItensSplitted = splitted[SALE_ITENS].replace("[", "").replace("]", "").split(",");
				for (String saleItemLine : saleItensSplitted) {
					String[] splittedItem = saleItemLine.split("-");
					
					SaleItem saleItem = createSaleItem(splittedItem);
					saleItens.add(saleItem);
				}
				
				Sale sale = new Sale(Long.parseLong(splitted[SALE_ID]), saleItens, saleSalesman);
				logger.info("{}", sale.toString());
				
				return sale;
	
			default:
				logger.error("Error when trying to read line: {}", line);
				logger.error("Error in line: {}", line);
				
				throw new InvalidParameterException("Invalid line: ".concat(line));
		}
	}
	
	private SaleItem createSaleItem(String[] lineSplitted) {
		return new SaleItem(Long.parseLong(lineSplitted[SALE_ITEM_ID]), new BigDecimal(lineSplitted[SALE_ITEM_QUANTITY]), new BigDecimal(lineSplitted[SALE_ITEM_PRICE]));
	}

}
