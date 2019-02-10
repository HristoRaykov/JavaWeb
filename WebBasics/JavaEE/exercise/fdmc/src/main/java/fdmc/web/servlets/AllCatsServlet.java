package fdmc.web.servlets;

import fdmc.domain.entities.Cat;
import fdmc.util.HtmlReader;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/cats/all")
@SuppressWarnings("unchecked")
public class AllCatsServlet extends HttpServlet {

	private static final String CATS_ALL_HTML_PATH = "/home/hristocr/GoogleDrive/SoftUni/Modules/JavaWeb 01.2019/WebBasics/JavaEE/exercise/fdmc/src/main/resources/views/cats-all.html";
	
	private final HtmlReader htmlReader;
	
	@Inject
	public AllCatsServlet(HtmlReader htmlReader) {
		this.htmlReader = htmlReader;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext context = this.getServletContext();
		Map<String, Cat> cats = (Map<String, Cat>) context.getAttribute("cats");
		
		String htmlText = htmlReader.readHtmlFile(CATS_ALL_HTML_PATH);
		StringBuilder catListHtml = new StringBuilder();
		if (cats == null || cats.size()==0){
			catListHtml.append("<h4>There are no cats. <a href=\"/cats/create\">Create some!</a></h4><br>");
			htmlText = String.format(htmlText,catListHtml.toString());
			resp.getWriter().println(htmlText);
			return;
		}
		
		for (Cat cat : cats.values()) {
			catListHtml.append("<a href=\"/cats/profile?catName=")
					.append(cat.getName())
					.append("\">")
					.append(cat.getName())
					.append("</a><br>")
					.append(System.lineSeparator());
		}
		
		htmlText = String.format(htmlText,catListHtml.toString());
		resp.getWriter().println(htmlText);
	
	}
}
