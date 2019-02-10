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
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/cats/profile")
public class CatProfileServlet extends HttpServlet {
	
	private static final String CAT_PROFILE_HTML_PATH = "/home/hristocr/GoogleDrive/SoftUni/Modules/JavaWeb 01.2019/WebBasics/JavaEE/exercise/fdmc/src/main/resources/views/cat-profile.html";
	private static final String CAT_NONEXISTENT_HTML_PATH = "/home/hristocr/GoogleDrive/SoftUni/Modules/JavaWeb 01.2019/WebBasics/JavaEE/exercise/fdmc/src/main/resources/views/cat-nonexistent.html";
	
	private final HtmlReader htmlReader;
	
	@Inject
	public CatProfileServlet(HtmlReader htmlReader) {
		this.htmlReader = htmlReader;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext context = this.getServletContext();
		PrintWriter writer = resp.getWriter();
		Map<String, Cat> cats = (Map<String, Cat>) context.getAttribute("cats");
		
		String catName = (String) req.getParameter("catName");
		Cat cat = cats.get(catName);
		if (cat==null){
			String htmlNonexistent = htmlReader.readHtmlFile(CAT_NONEXISTENT_HTML_PATH);
			htmlNonexistent = String.format(htmlNonexistent,catName);
			writer.println(htmlNonexistent);
			return;
		}
		String htmlProfile = htmlReader.readHtmlFile(CAT_PROFILE_HTML_PATH);
		htmlProfile = String.format(htmlProfile, cat.getName(),cat.getBreed(),cat.getColor(),cat.getAge());
		writer.println(htmlProfile);
		
	}
}
