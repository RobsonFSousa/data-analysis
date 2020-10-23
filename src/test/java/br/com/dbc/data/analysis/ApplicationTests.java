package br.com.dbc.data.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.dbc.data.analysis.consts.DefaultFilePath;
import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.models.Report;
import br.com.dbc.data.analysis.models.Salesman;
import br.com.dbc.data.analysis.services.FileProcessorService;
import br.com.dbc.data.analysis.services.FileService;
import br.com.dbc.data.analysis.services.ReportService;

@SpringBootTest
class ApplicationTests {
	
	@Autowired
	private FileProcessorService fileProcessorService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ReportService reportService;
	
	private final String filePath = DefaultFilePath.INPUT.concat("sale.dat");
	private final List<String> fileLines = new ArrayList<String>() {
		{
			add("001ç1234567891234çPedroç50000");
			add("001ç3245678865434çPauloç40000.99");
			add("002ç2345675434544345çJose da SilvaçRural");
			add("002ç2345675433444345çEduardo PereiraçRural");
			add("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro");
			add("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");
		}
	};
	
	@Test
	void processFilesDBCFromDirectory_ShoudProcessFile() throws IOException {		
		FileDBC filesDBC = fileProcessorService.processDBCFile(filePath, fileLines);
		
		Assertions.assertNotNull(filesDBC);
	}
	
	@Test
	void processFilesDBCFromDirectory_ShoudGetCustomersQuantity() throws IOException {
		FileDBC fileDBC = fileProcessorService.processDBCFile(filePath, fileLines);
		Assertions.assertEquals(fileDBC.getCustomers().size(), 2);
	}
	
	@Test
	void processFilesDBCFromDirectory_ShoudGetSalesmanQuantity() throws IOException{
		FileDBC fileDBC = fileProcessorService.processDBCFile(filePath, fileLines);
		Assertions.assertEquals(fileDBC.getSalesmans().size(), 2);
	}
	
	@Test
	void processFilesDBCFromDirectory_ShoudGetMostExpensiveSaleId() throws IOException{
		FileDBC fileDbc = fileProcessorService.processDBCFile(filePath, fileLines);
		long mostExpensiveSaleId = fileService.getMostExpensiveSale(fileDbc).getId();
		Assertions.assertEquals(mostExpensiveSaleId, 10);
	}
	
	@Test
	void processFilesDBCFromDirectory_ShoudGetWorstSalesmanName() throws IOException{
		FileDBC fileDbc = fileProcessorService.processDBCFile(filePath, fileLines);
		String worstSalesman = fileService.getWorstSalesman(fileDbc).getName();
		Assertions.assertEquals(worstSalesman, "Paulo");
	}
	
	@Test
	void processFilesDBCFromDirectory_ShoudGenerateReport() throws IOException{
		FileDBC fileDbc = fileProcessorService.processDBCFile(filePath, fileLines);
		
		Salesman wrostSalesman = fileService.getWorstSalesman(fileDbc);
    	Long mostExpensiveSaleId = fileService.getMostExpensiveSale(fileDbc).getId();
		
		Report report = reportService.generateReport(fileDbc.getCustomers().size(), fileDbc.getSalesmans().size(), mostExpensiveSaleId, wrostSalesman);
		
		Assertions.assertEquals(report.getCustomersQuantity(), 2);
		Assertions.assertEquals(report.getMostExpensiveSaleId(), 10);
		Assertions.assertEquals(report.getSalesmansQuantity(), 2);
		Assertions.assertEquals(report.getWrostSalesman().getName(), "Paulo");
	}
	
	@Test
	void processFilesDBCFromDirectory_ShoudGenerateOutputFile() throws IOException {
		
		FileDBC fileDBC = fileProcessorService.processDBCFile(filePath, fileLines);
		boolean reportGenerated = fileService.generateOutputFile(fileDBC, DefaultFilePath.OUTPUT);
		Assertions.assertTrue(reportGenerated);
	}

}
