package br.com.dbc.data.analysis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dbc.data.analysis.models.Sale;
import br.com.dbc.data.analysis.repositories.SaleRepository;
import br.com.dbc.data.analysis.services.SaleService;

@Service
public class SaleServiceImpl implements SaleService {
	@Autowired 
	SaleRepository saleRepository;

	@Override
	public Sale save(Sale sale) {
		return saleRepository.save(sale);
	}

}
