package chushka.web.servlets;

import chushka.domain.entities.ProdType;
import chushka.domain.models.binding.CreateProductBindingModel;
import chushka.domain.models.service.ProductServiceModel;
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
import java.util.Arrays;

@WebServlet("/products/create")
public class ProductCreateServlet extends HttpServlet {
	
	private final HtmlReader htmlReader;
	private final ProductService productService;
	private final ModelMapper modelMapper;
	
	@Inject
	public ProductCreateServlet(HtmlReader htmlReader, ProductService productService, ModelMapper modelMapper) {
		this.htmlReader = htmlReader;
		this.productService = productService;
		this.modelMapper = modelMapper;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String viewContent = this.htmlReader.readHtmlFile("create-product");
		String selectOptionHtml = this.getSelectOptionHtml();
		viewContent = viewContent.replace("{{options}}",selectOptionHtml);
		
		resp.getWriter().println(viewContent);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CreateProductBindingModel createProductBindingModel = new CreateProductBindingModel();
		createProductBindingModel.setName(req.getParameter("name"));
		createProductBindingModel.setDescription(req.getParameter("description"));
		createProductBindingModel.setType(req.getParameter("productTypeInput"));
		
		ProductServiceModel productServiceModel = this.modelMapper.map(createProductBindingModel,ProductServiceModel.class);
		this.productService.saveProduct(productServiceModel);
		
		resp.sendRedirect("/");
	}
	
	private String getSelectOptionHtml() {
		StringBuilder resultString = new StringBuilder();
		Arrays.stream(ProdType.values())
				.forEach(prodType -> resultString.append("<option>")
						.append(prodType.name())
						.append("</option>")
				);
		return resultString.toString();
	}
}
