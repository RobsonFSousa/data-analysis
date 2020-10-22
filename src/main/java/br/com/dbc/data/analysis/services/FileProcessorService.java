package br.com.dbc.data.analysis.services;

import br.com.dbc.data.analysis.models.FileDBC;

public interface FileProcessorService {
	/**
	 * Process the DBC file from path.
	 * @param filePath
	 * @return
	 */
	public FileDBC processDBCFile(String filePath);

}
