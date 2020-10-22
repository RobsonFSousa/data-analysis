package br.com.dbc.data.analysis;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.dbc.data.analysis.consts.DefaultFilePath;
import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.models.Report;
import br.com.dbc.data.analysis.services.FileProcessorService;
import br.com.dbc.data.analysis.services.FileService;

@SpringBootTest
class ApplicationTests {
	
	@Autowired
	private FileProcessorService fileProcessorService;
	
	@Autowired
	private FileService fileService;
	
	private List<String> fileLines;
	private final String filePath = DefaultFilePath.INPUT.concat("sale.dat");
	
	@Before
	public void setUp() throws IOException {
		fileLines = fileService.loadFile(filePath);
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudProcessFile() throws IOException {
		FileDBC filesDBC = fileProcessorService.processDBCFile(filePath, fileLines);
		
		Assertions.assertNotNull(filesDBC);
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudGetCustomersQuantity() throws IOException {
		FileDBC fileDBC = fileProcessorService.processDBCFile(filePath, fileLines);
		Assertions.assertEquals(fileDBC.getCustomers().size(), 2);
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudGetSalesmanQuantity() throws IOException{
		FileDBC fileDBC = fileProcessorService.processDBCFile(filePath, fileLines);
		Assertions.assertEquals(fileDBC.getSalesmans().size(), 2);
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudGetMostExpensiveSaleId() throws IOException{
		FileDBC fileDbc = fileProcessorService.processDBCFile(filePath, fileLines);
		long mostExpensiveSaleId = fileService.getMostExpensiveSale(fileDbc).getId();
		Assertions.assertEquals(mostExpensiveSaleId, 10);
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudGetWorstSalesmanName() throws IOException{
		FileDBC fileDbc = fileProcessorService.processDBCFile(filePath, fileLines);
		String worstSalesman = fileService.getWorstSalesman(fileDbc).getName();
		Assertions.assertEquals(worstSalesman, "Paulo");
	}
	
	/*@Test
	void ProcessFilesDBCFromDirectory_ShoudGenerateReport() throws IOException{
		fileProcessorService.processDBCFile(Consts.INPUT_FILES_PATH);
		Report report = fileProcessorService.GenerateReport();
		
		Assertions.assertEquals(report.getProcessedFilesSummary().get(0).getCustomersQuantity(), 2);
		Assertions.assertEquals(report.getProcessedFilesSummary().get(0).getMostExpensiveSaleId(), 10);
		Assertions.assertEquals(report.getProcessedFilesSummary().get(0).getSalesmansQuantity(), 2);
		Assertions.assertEquals(report.getProcessedFilesSummary().get(0).getWrostSalesman().getName(), "Paulo");
	}*/
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudGenerateOutputFile() throws IOException {
		
		FileDBC fileDBC = fileProcessorService.processDBCFile(filePath, fileLines);
		boolean reportGenerated = fileService.generateOutputFile(fileDBC, DefaultFilePath.OUTPUT);
		Assertions.assertTrue(reportGenerated);
	}

}
