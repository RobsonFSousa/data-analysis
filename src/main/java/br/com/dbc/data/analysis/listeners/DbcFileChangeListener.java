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
import br.com.dbc.data.analysis.services.FileProcessorService;
import br.com.dbc.data.analysis.services.FileService;

@Component
public class DbcFileChangeListener implements FileChangeListener {
	private static Logger logger = LoggerFactory.getLogger(DbcFileChangeListener.class);
	private static FileProcessorService fileProcessorService;
	private static FileService fileService;
	
	public DbcFileChangeListener(FileProcessorService fileProcessorService, FileService fileUtil) {
		DbcFileChangeListener.fileProcessorService = fileProcessorService;
		DbcFileChangeListener.fileService = fileUtil;
	}

	@Override
	public void onChange(Set<ChangedFiles> changeSet) {
		for(ChangedFiles cfiles : changeSet) {
            for(ChangedFile cfile: cfiles.getFiles()) {
            	// Filter for .dat files only and new files added
                if(cfile.getFile().getName().toLowerCase().endsWith(".dat") && cfile.getType().equals(Type.ADD)) {
                	logger.info("File add in path: {}", cfile.getFile().getAbsolutePath());
                	
                	List<String> fileLines;
                	String filePath = cfile.getFile().getAbsolutePath();
					try {
						fileLines = fileService.loadFile(filePath);
						
						FileDBC fileProcessed = fileProcessorService.processDBCFile(filePath, fileLines);
						fileProcessed.setPath(cfile.getFile().getAbsolutePath());
						
	                	fileService.generateOutputFile(fileProcessed, DefaultFilePath.OUTPUT);
					} catch (IOException e) {
						logger.error("Error when trying to process the file in path: {}", filePath);
						e.printStackTrace();
					}
                	
                }
            }
        }
	}
}
