package chushka.repository;

import chushka.domain.entities.Product;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
	
	private static final String FIND_ALL_QUERY ="select p from products p";
	private static final String FIND_PRODUCT_BY_NAME_QUERY = "select p from products p where p.name=:name";
	
	private EntityManager entityManager;
	
	public ProductRepositoryImpl() {
		this.entityManager = Persistence
				.createEntityManagerFactory("chushka")
				.createEntityManager();
		
	}
	
	@Override
	public Product save(Product entity) {
		this.entityManager.getTransaction().begin();
		this.entityManager.persist(entity);
		this.entityManager.getTransaction().commit();
		
		return entity;
	}
	
	@Override
	public Product findById(String id) {
		this.entityManager.getTransaction().begin();
		Product product = this.entityManager.find(Product.class,id);
		this.entityManager.getTransaction().commit();
		
		return product;
	}
	
	@Override
	public List<Product> findAll() {
		this.entityManager.getTransaction().begin();
		
		List<Product> products = this.entityManager
				 .createQuery(FIND_ALL_QUERY,Product.class)
				 .getResultList();
		
		this.entityManager.getTransaction().commit();
		
		return products;
	}
	
	@Override
	public Product findByName(String name){
		this.entityManager.getTransaction().begin();
		Product product = this.entityManager
				.createQuery(FIND_PRODUCT_BY_NAME_QUERY,Product.class)
				.setParameter("name",name)
				.getSingleResult();
		
		this.entityManager.getTransaction().commit();
		
		return product;
	}
}
