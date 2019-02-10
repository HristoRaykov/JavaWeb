package chushka.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.stream.Collectors;

public class HtmlReaderImpl implements HtmlReader {

	
	@Override
	public String readHtmlFile(String viewName) throws IOException {
		var viewPath = MessageFormat.format("views/{0}.html",viewName);
		
		URL url = this.getClass().getClassLoader().getResource(viewPath);
		
		File file;
		try {
			file= new File(url.toURI());
		} catch (URISyntaxException e) {
			file=new File((url.getPath()));
		}
		
		return Files.lines(Paths.get(file.getAbsolutePath())).collect(Collectors.joining(System.lineSeparator()));
	}


}
