package br.com.dbc.data.analysis.services.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.dbc.data.analysis.models.Report;
import br.com.dbc.data.analysis.models.Salesman;
import br.com.dbc.data.analysis.services.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	private static Logger logger = LoggerFactory.getLogger(FileProcessorServiceImpl.class);

	@Override
	public Report generateReport(long customersQuantity, long salesmanQuantity, long mostExpensiveSaleId, Salesman wrostSalesman) {
		Report report = new Report(0, 0, 0, new Salesman("", "", new BigDecimal(0)));
		
		logger.info("Generating Report...");
		
		report.setCustomersQuantity(customersQuantity);
		report.setMostExpensiveSaleId(mostExpensiveSaleId);
		report.setSalesmansQuantity(salesmanQuantity);
		report.setWrostSalesman(wrostSalesman);
		
		logger.info("Report successfully generated.");
		
		return report;
	}

}
