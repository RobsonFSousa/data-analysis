package br.com.dbc.data.analysis.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class FileUtil {
	
	public Map<String, List<String>> loadAllFiles(String directory)
	{
		Map<String, List<String>> filesMap = new HashMap<String, List<String>>();
		
		try {
			File path = new File(directory);
			// Filter for .dat only
			File files[] = path.listFiles((dir, name) -> name.toLowerCase().endsWith(".dat"));

			for(File file : files) {
				String filePath = file.getAbsolutePath();
				String lines = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.ISO_8859_1);
				List<String> list = new ArrayList<String>(Arrays.asList(lines.split("\n")));
				
				filesMap.put(filePath, list);
	      }

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return filesMap;
	}
}
