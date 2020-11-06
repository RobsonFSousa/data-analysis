package br.com.dbc.data.analysis.listeners;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFile.Type;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import br.com.dbc.data.analysis.consts.DefaultFilePath;
import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.models.Report;
import br.com.dbc.data.analysis.models.Salesman;
import br.com.dbc.data.analysis.services.FileProcessorService;
import br.com.dbc.data.analysis.services.FileService;
import br.com.dbc.data.analysis.services.ReportService;

@Component
public class DbcFileChangeListener implements FileChangeListener {
	private static Logger logger = LoggerFactory.getLogger(DbcFileChangeListener.class);
	private static FileProcessorService fileProcessorService;
	private static FileService fileService;
	private static ReportService reportService;
	
	public DbcFileChangeListener(FileProcessorService fileProcessorService, FileService fileUtil, ReportService reportService) {
		DbcFileChangeListener.fileProcessorService = fileProcessorService;
		DbcFileChangeListener.fileService = fileUtil;
		DbcFileChangeListener.reportService = reportService;
	}

	@Override
	public void onChange(Set<ChangedFiles> changeSet) {
		for(ChangedFiles cfiles : changeSet) {
            for(ChangedFile cfile: cfiles.getFiles()) {
                if(cfile.getFile().getName().toLowerCase().endsWith(".dat") && cfile.getType().equals(Type.ADD)) {
                	logger.info("File add in path: {}", cfile.getFile().getAbsolutePath());
                	
                	List<String> fileLines = null;
                	String filePath = cfile.getFile().getAbsolutePath();
					try {
						FileDBC fileProcessed = processFile(fileLines, filePath);
						Salesman wrostSalesman = fileProcessorService.getWorstSalesman(fileProcessed);
	                	Long mostExpensiveSaleId = fileProcessorService.getMostExpensiveSale(fileProcessed).getId();
	                	
						fileService.generateOutputFile(DefaultFilePath.OUTPUT, fileProcessed.getCustomers().size(), 
								 fileProcessed.getSalesmans().size(), 
								 mostExpensiveSaleId, 
								 wrostSalesman);
	                	
						printReport(fileProcessed.getCustomers().size(), 
									fileProcessed.getSalesmans().size(), 
									mostExpensiveSaleId, 
									wrostSalesman);
					} catch (IOException e) {
						logger.error("Error when trying to process the file in path: {}", filePath);
						e.printStackTrace();
					}
                	
                }
            }
        }
	}
	
	private FileDBC processFile(List<String> fileLines, String filePath) throws IOException {
		FileDBC fileProcessed = new FileDBC();
		fileLines = fileService.loadFile(filePath);
		fileProcessorService.processDBCFile(filePath, fileLines);
		fileProcessed.setPath(filePath);
		
		return fileProcessed;
	}
	
	private void printReport(long customersQuantity, long salesmanQuantity, long mostExpensiveSaleId, Salesman wrostSalesman) {
		Report report = reportService.generateReport(customersQuantity, 
													 salesmanQuantity, 
													 mostExpensiveSaleId, 
													 wrostSalesman);
		logger.info(report.toString());
	}
}
