package br.com.dbc.data.analysis.config;

import java.io.File;
import java.time.Duration;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.dbc.data.analysis.consts.DefaultFilePath;
import br.com.dbc.data.analysis.listeners.DbcFileChangeListener;
import br.com.dbc.data.analysis.services.FileProcessorService;
import br.com.dbc.data.analysis.services.impl.FileServiceImpl;

@Configuration
public class FileWatcherConfiguration {
	@Autowired
	FileProcessorService fileProcessorService;
	
	@Autowired
	FileServiceImpl fileUtil;
	
	@Bean
    public FileSystemWatcher fileSystemWatcher() {		
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofSeconds(1), Duration.ofMillis(500L));
        fileSystemWatcher.addSourceDirectory(new File(DefaultFilePath.INPUT));
        fileSystemWatcher.addListener(new DbcFileChangeListener(fileProcessorService, fileUtil));
        fileSystemWatcher.start();

        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() throws Exception {
        fileSystemWatcher().stop();
    }
}
