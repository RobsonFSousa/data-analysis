package br.com.dbc.data.analysis.services;

import java.util.List;

import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.models.Sale;
import br.com.dbc.data.analysis.models.Salesman;

public interface FileProcessorService {
	
	public FileDBC processDBCFile(String filePath, List<String> lines);
	public Salesman getWorstSalesman(FileDBC fileDbc);
	public Sale getMostExpensiveSale(FileDBC fileDbc);
}
