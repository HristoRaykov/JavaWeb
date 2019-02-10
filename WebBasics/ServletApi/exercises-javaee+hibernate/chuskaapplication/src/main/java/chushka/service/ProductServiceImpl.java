package chushka.service;

import chushka.domain.entities.Product;
import chushka.domain.entities.ProdType;
import chushka.domain.models.service.ProductServiceModel;
import chushka.domain.models.view.ProductDetailsViewModel;
import chushka.repository.ProductRepository;
import chushka.util.ModelMapper;
import org.modelmapper.TypeToken;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class ProductServiceImpl implements ProductService {
	
	
	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;
	
	@Inject
	public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
		this.productRepository = productRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public void saveProduct(ProductServiceModel productServiceModel) {
		Product product = this.modelMapper.map(productServiceModel, Product.class);
		product.setProdType(ProdType.valueOf(productServiceModel.getType()));
		this.productRepository.save(product);
	}
	
	@Override
	public List<ProductServiceModel> getAllProducts(){
		List<Product> products = this.productRepository.findAll();
//		Type listType = new TypeToken<List<ProductServiceModel>>(){}.getType();
		List<ProductServiceModel> productServiceModels =
				Arrays.asList(this.modelMapper.map(products,ProductServiceModel[].class));
		return productServiceModels;
	}
	
	@Override
	public ProductServiceModel getProductByName(String name) {
		Product product = this.productRepository.findByName(name);
		ProductServiceModel productServiceModel = this.modelMapper.map(product,ProductServiceModel.class);
		
		return productServiceModel;
	}
}
