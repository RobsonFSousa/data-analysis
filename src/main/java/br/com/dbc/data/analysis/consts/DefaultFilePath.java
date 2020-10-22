package br.com.dbc.data.analysis.consts;

import java.io.File;

public abstract class DefaultFilePath {
	public final static String INPUT = System.getProperty("user.home")
			.concat(File.separator)
			.concat("data")
			.concat(File.separator)
			.concat("in")
			.concat(File.separator);
	public final static String OUTPUT = System.getProperty("user.home")
			.concat(File.separator)
			.concat("data")
			.concat(File.separator)
			.concat("out")
			.concat(File.separator);
}
