package fdmc.util;

import java.io.*;

public class HtmlReaderImpl implements HtmlReader {
	
	@Override
	public String readHtmlFile(String filePath) throws IOException {
		File file = new File(filePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		StringBuilder htmlText = new StringBuilder();
		String line;
		while ((line=reader.readLine())!=null){
			htmlText.append(line).append(System.lineSeparator());
		}
		
		return htmlText.toString().trim();
	}
	
	
}
