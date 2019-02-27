package springmvc.realestateagency.util;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HtmlFileReaderImpl implements HtmlFileReader {
	
	@Override
	public String readHtmlFromFile(String filename) throws IOException {
		String filePath = String.format("static/html/%s.html",filename);
		File file = new ClassPathResource(filePath).getFile();
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder fileContent = new StringBuilder();
		String line;
		while ((line=reader.readLine())!=null){
			fileContent.append(line).append(System.lineSeparator());
		}
		return fileContent.toString();
	}
	
	
	
}
