package fdmc.web.servlets;

import fdmc.util.HtmlReader;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class IndexServlet extends HttpServlet {
	
	private static final String INDEX_HTML_FILE_PATH = "/home/hristocr/GoogleDrive/SoftUni/Modules/JavaWeb 01.2019/WebBasics/JavaEE/exercise/fdmc/src/main/resources/views/index.html";
	private final HtmlReader htmlReader;
	
	@Inject
	public IndexServlet(HtmlReader htmlReader) {
		this.htmlReader = htmlReader;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter writer = resp.getWriter();
		String htmlText = this.htmlReader.readHtmlFile(INDEX_HTML_FILE_PATH);
		
		writer.println(htmlText);
	}
}
