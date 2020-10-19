package br.com.dbc.data.analysis;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.models.Report;
import br.com.dbc.data.analysis.utils.Consts;
import br.com.dbc.data.analysis.utils.FileProcessor;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private FileProcessor fileProcessor;
	

	@Test
	void ProcessFilesDBCFromDirectory_ShouldRespondsHTTP200() throws Exception {
		mockMvc.perform(get("/process-files"))
		.andExpect(status().isOk());
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudProcessFile() throws IOException {
		List<FileDBC> filesDBC = fileProcessor.ProcessFilesDBCFromDirectory(Consts.INPUT_FILES_PATH);
		Assertions.assertEquals(filesDBC.size(), 1);
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudGetCustomersQuantity() throws IOException {
		List<FileDBC> filesDBC = fileProcessor.ProcessFilesDBCFromDirectory(Consts.INPUT_FILES_PATH);
		Assertions.assertEquals(filesDBC.get(0).getCustomers().size(), 2);
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudGetSalesmanQuantity() throws IOException{
		List<FileDBC> filesDBC = fileProcessor.ProcessFilesDBCFromDirectory(Consts.INPUT_FILES_PATH);
		Assertions.assertEquals(filesDBC.get(0).getSalesmans().size(), 2);
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudGetMostExpensiveSaleId() throws IOException{
		List<FileDBC> filesDBC = fileProcessor.ProcessFilesDBCFromDirectory(Consts.INPUT_FILES_PATH);
		Assertions.assertEquals(filesDBC.get(0).GetMostExpensiveSale().getId(), 10);
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudGetWorstSalesmanName() throws IOException{
		List<FileDBC> filesDBC = fileProcessor.ProcessFilesDBCFromDirectory(Consts.INPUT_FILES_PATH);
		Assertions.assertEquals(filesDBC.get(0).GetWorstSalesman().getName(), "Paulo");
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudGenerateReport() throws IOException{
		fileProcessor.ProcessFilesDBCFromDirectory(Consts.INPUT_FILES_PATH);
		Report report = fileProcessor.GenerateReport();
		
		Assertions.assertEquals(report.getProcessedFilesSummary().get(0).getCustomersQuantity(), 2);
		Assertions.assertEquals(report.getProcessedFilesSummary().get(0).getMostExpensiveSaleId(), 10);
		Assertions.assertEquals(report.getProcessedFilesSummary().get(0).getSalesmansQuantity(), 2);
		Assertions.assertEquals(report.getProcessedFilesSummary().get(0).getWrostSalesman().getName(), "Paulo");
	}
	
	@Test
	void ProcessFilesDBCFromDirectory_ShoudGenerateOutputFile() throws IOException {
		
		List<FileDBC> filesDBC = fileProcessor.ProcessFilesDBCFromDirectory(Consts.INPUT_FILES_PATH);
		boolean reportGenerated = fileProcessor.GenerateOutputFile(Consts.OUTPUT_FILES_PATH, filesDBC.get(0));
		Assertions.assertTrue(reportGenerated);
	}

}
