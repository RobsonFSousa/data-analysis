package br.com.dbc.data.analysis.services;

import java.io.IOException;
import java.util.List;

import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.models.Sale;
import br.com.dbc.data.analysis.models.Salesman;

public interface FileService {
	/**
	 * Load file from a path
	 * @param directory
	 * @return
	 * @throws IOException
	 */
	public List<String> loadFile(String filePath) throws IOException;
	
	/**
	 * Generate the output files.
	 * @param fileDBC
	 * @param output
	 * @return
	 */
	public boolean generateOutputFile(FileDBC fileDbc, String output);
	
	/**
	 * Gets the worst Salesman from the file.
	 * @param fileDbc
	 * @return
	 */
	public Salesman getWorstSalesman(FileDBC fileDbc);
	
	/**
	 * Gets the most expensive sale from the file.
	 * @param fileDbc
	 * @return
	 */
	public Sale getMostExpensiveSale(FileDBC fileDbc);
}
