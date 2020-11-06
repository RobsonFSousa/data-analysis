package br.com.dbc.data.analysis.services;

import java.io.IOException;
import java.util.List;

import br.com.dbc.data.analysis.models.Salesman;

public interface FileService {

	public List<String> loadFile(String filePath) throws IOException;
	public boolean generateOutputFile(String output, long customersQuantity, long salesmanQuantity, long mostExpensiveSaleId, Salesman wrostSalesman);
}
