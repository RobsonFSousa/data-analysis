package br.com.dbc.data.analysis.listeners;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFile.Type;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import br.com.dbc.data.analysis.models.FileDBC;
import br.com.dbc.data.analysis.services.FileProcessorService;
import br.com.dbc.data.analysis.utils.Consts;
import br.com.dbc.data.analysis.utils.FileUtil;

@Component
public class DbcFileChangeListener implements FileChangeListener {
	private static Logger logger = LoggerFactory.getLogger(DbcFileChangeListener.class);
	private static FileProcessorService fileProcessorService;
	private static FileUtil fileUtil;
	
	public DbcFileChangeListener(FileProcessorService fileProcessorService, FileUtil fileUtil) {
		DbcFileChangeListener.fileProcessorService = fileProcessorService;
		DbcFileChangeListener.fileUtil = fileUtil;
	}

	@Override
	public void onChange(Set<ChangedFiles> changeSet) {
		for(ChangedFiles cfiles : changeSet) {
            for(ChangedFile cfile: cfiles.getFiles()) {
                if( cfile.getType().equals(Type.ADD)) {
                	logger.info("File add in path: {}", cfile.getFile().getAbsolutePath());
                	
                	FileDBC fileProcessed = fileProcessorService.processDBCFile(cfile.getFile().getAbsolutePath());
                	fileUtil.GenerateOutputFile(Consts.OUTPUT_FILES_PATH, fileProcessed);
                }
            }
        }
	}
}
