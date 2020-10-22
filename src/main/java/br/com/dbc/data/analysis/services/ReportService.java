package br.com.dbc.data.analysis.services;

import br.com.dbc.data.analysis.models.Report;
import br.com.dbc.data.analysis.models.Salesman;

public interface ReportService {
	/**
	 * Generate the report.
	 * @param customersQuantity
	 * @param salesmanQuantity
	 * @param mostExpensiveSaleId
	 * @param wrostSalesman
	 * @return
	 */
	public Report generateReport(long customersQuantity, long salesmanQuantity, long mostExpensiveSaleId, Salesman wrostSalesman);
}
