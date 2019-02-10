package chushka.web.servlets;

import chushka.domain.models.service.ProductServiceModel;
import chushka.domain.models.view.ProductDetailsViewModel;
import chushka.service.ProductService;
import chushka.util.HtmlReader;
import chushka.util.ModelMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/products/details")
public class ProductDetailsServlet extends HttpServlet {
	
	private final ProductService productService;
	private final HtmlReader htmlReader;
	private final ModelMapper modelMapper;
	
	@Inject
	public ProductDetailsServlet(ProductService productService, HtmlReader htmlReader, ModelMapper modelMapper) {
		this.productService = productService;
		this.htmlReader = htmlReader;
		this.modelMapper = modelMapper;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		ProductServiceModel productServiceModel = this.productService.getProductByName(name);
		ProductDetailsViewModel productDetailsViewModel = this.modelMapper.map(productServiceModel,ProductDetailsViewModel.class);
		String htmlContent = this.htmlReader.readHtmlFile("details-product");
		htmlContent = htmlContent.replace("{{name}}",productDetailsViewModel.getName());
		htmlContent = htmlContent.replace("{{description}}",productDetailsViewModel.getDescription());
		htmlContent = htmlContent.replace("{{type}}",productDetailsViewModel.getType());
		
		resp.getWriter().println(htmlContent);
	}
}
