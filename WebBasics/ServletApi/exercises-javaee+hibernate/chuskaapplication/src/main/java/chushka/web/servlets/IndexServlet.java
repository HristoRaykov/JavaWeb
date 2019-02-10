package chushka.web.servlets;

import chushka.domain.entities.Product;
import chushka.domain.models.service.ProductServiceModel;
import chushka.domain.models.view.AllProductViewModel;
import chushka.service.ProductService;
import chushka.util.HtmlReader;
import chushka.util.ModelMapper;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/")
public class IndexServlet extends HttpServlet {
	
	private static final String PRODUCT_DETAILS_LINK_LINE =
					"<div class=\"row\">\n" +
					"<li><a href=\"/products/details?name=%s\">%s</a></li>\n" +
					"</div>";
	
	
	private final ProductService productService;
	private final HtmlReader htmlReader;
	private final ModelMapper modelMapper;
	
	@Inject
	public IndexServlet(ProductService productService, HtmlReader htmlReader, ModelMapper modelMapper) {
		this.productService = productService;
		this.htmlReader = htmlReader;
		this.modelMapper = modelMapper;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String htmlContent = this.htmlReader.readHtmlFile("index");
		List<ProductServiceModel> productServiceModels = this.productService.getAllProducts();
		if (productServiceModels.isEmpty()) {
			htmlContent = htmlContent.replace("{{products}}", "");
			resp.getWriter().println(htmlContent);
			return;
		}
		List<AllProductViewModel> allProductViewModels = Arrays.asList(this.modelMapper.map(productServiceModels,AllProductViewModel[].class));
		String productList = this.getProductListHtml(allProductViewModels);
		htmlContent = htmlContent.replace("{{products}}", productList);
		
		resp.getWriter().println(htmlContent);
	}
	
	private String getProductListHtml(List<AllProductViewModel> allProductViewModels) {
		StringBuilder resultString = new StringBuilder();
		allProductViewModels
				.forEach(p -> resultString.
						append(String.format(PRODUCT_DETAILS_LINK_LINE, p.getName(), p.getName())));
		
		return resultString.toString();
	}
}
