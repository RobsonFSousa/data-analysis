package br.com.dbc.data.analysis;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.models.Report;
import br.com.dbc.data.analysis.services.impl.FileProcessorServiceImpl;
import br.com.dbc.data.analysis.utils.Consts;
import br.com.dbc.data.analysis.utils.FileUtil;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private FileProcessorServiceImpl fileProcessorService;
	
	@Autowired
	private FileUtil fileUtil;

	@Test
	void ProcessFilesDBCFromDirectory_ShouldRespondsHTTP200() throws Exception {
		mockMvc.perform(get("/process-files"))
		.andExpect(status().isOk());
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudProcessFile() throws IOException {
		FileDBC filesDBC = fileProcessorService.processDBCFile(Consts.INPUT_FILES_PATH);
		Assertions.assertNotNull(filesDBC.getSales());
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudGetCustomersQuantity() throws IOException {
		FileDBC fileDBC = fileProcessorService.processDBCFile(Consts.INPUT_FILES_PATH);
		Assertions.assertEquals(fileDBC.getCustomers().size(), 2);
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudGetSalesmanQuantity() throws IOException{
		FileDBC fileDBC = fileProcessorService.processDBCFile(Consts.INPUT_FILES_PATH);
		Assertions.assertEquals(fileDBC.getSalesmans().size(), 2);
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudGetMostExpensiveSaleId() throws IOException{
		FileDBC fileDBC = fileProcessorService.processDBCFile(Consts.INPUT_FILES_PATH);
		Assertions.assertEquals(fileDBC.GetMostExpensiveSale().getId(), 10);
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudGetWorstSalesmanName() throws IOException{
		FileDBC fileDBC = fileProcessorService.processDBCFile(Consts.INPUT_FILES_PATH);
		Assertions.assertEquals(fileDBC.GetWorstSalesman().getName(), "Paulo");
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
		
		FileDBC fileDBC = fileProcessorService.processDBCFile(Consts.INPUT_FILES_PATH);
		boolean reportGenerated = fileUtil.GenerateOutputFile(Consts.OUTPUT_FILES_PATH, fileDBC);
		Assertions.assertTrue(reportGenerated);
	}

}
