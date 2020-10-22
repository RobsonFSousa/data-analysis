package br.com.dbc.data.analysis.services;

import java.util.List;

import br.com.dbc.data.analysis.models.FileDBC;

public interface FileProcessorService {
	/**
	 * Process the lines of the batch file.
	 * @param filePath
	 * @param lines
	 * @return
	 */
	public FileDBC processDBCFile(String filePath, List<String> lines);

}
