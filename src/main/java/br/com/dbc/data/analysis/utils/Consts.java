package br.com.dbc.data.analysis.utils;

import java.io.File;

public abstract class Consts {
	public final static String INPUT_FILES_PATH = System.getProperty("user.home")
			.concat(File.separator)
			.concat("data")
			.concat(File.separator)
			.concat("in")
			.concat(File.separator);
	public final static String OUTPUT_FILES_PATH = System.getProperty("user.home")
			.concat(File.separator)
			.concat("data")
			.concat(File.separator)
			.concat("out")
			.concat(File.separator);
}
