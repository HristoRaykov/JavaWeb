package app.config;

import app.constants.GlobalConstants;
import org.modelmapper.ModelMapper;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class ApplicationBeanConfiguration {
	
	@Produces
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	
	@Produces
	public EntityManager entityManager(){
		return Persistence
				.createEntityManagerFactory(GlobalConstants.PERSISTENCE_UNIT_NAME)
				.createEntityManager();
	}
	
	
}
