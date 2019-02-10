package chushka.service;

import chushka.domain.models.service.ProductServiceModel;

import java.util.List;

public interface ProductService {
	
	void saveProduct(ProductServiceModel productServiceModel);
	
	List<ProductServiceModel> getAllProducts();
	
	ProductServiceModel getProductByName(String name);
}
