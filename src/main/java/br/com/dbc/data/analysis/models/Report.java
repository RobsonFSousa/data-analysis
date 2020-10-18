package br.com.dbc.data.analysis.models;

import java.util.List;

public class Report {
	
	private List<ProcessedFileSummary> processedFilesSummary;
	
	/** 
	* Class constructor.
	*/
	public Report(List<ProcessedFileSummary> processedFilesSummary) {
		this.processedFilesSummary = processedFilesSummary;
	}

	// Getters and Setters
	public List<ProcessedFileSummary> getProcessedFilesSummary() {
		return processedFilesSummary;
	}

	public void setProcessedFilesSummary(List<ProcessedFileSummary> processedFilesSummary) {
		this.processedFilesSummary = processedFilesSummary;
	}
}
