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
import java.util.*;

@WebServlet("/cats/create")
public class CatCreateServlet extends HttpServlet {
	
	private static final String CAT_CREATE_HTML_PATH = "/home/hristocr/GoogleDrive/SoftUni/Modules/JavaWeb 01.2019/WebBasics/JavaEE/exercise/fdmc/src/main/resources/views/cat-create.html";

	private final HtmlReader htmlReader;

	@Inject
	public CatCreateServlet(HtmlReader htmlReader) {
		this.htmlReader = htmlReader;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String htmlText = htmlReader.readHtmlFile(CAT_CREATE_HTML_PATH);
		PrintWriter writer = resp.getWriter();
		writer.println(htmlText);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext context = this.getServletContext();
		Cat cat = new Cat();
		cat.setName(req.getParameter("name"));
		cat.setBreed(req.getParameter("breed"));
		cat.setColor(req.getParameter("color"));
		cat.setAge(Integer.valueOf(req.getParameter("age")));
		
		if (context.getAttribute("cats")==null){
			context.setAttribute("cats",new LinkedHashMap<>());
		}
		Map<String,Cat> cats = (Map<String, Cat>) context.getAttribute("cats");
		
		
		cats.putIfAbsent(cat.getName(),cat);
		
		resp.sendRedirect(String.format("/cats/profile?catName=%s",cat.getName()));
		
	}
}
