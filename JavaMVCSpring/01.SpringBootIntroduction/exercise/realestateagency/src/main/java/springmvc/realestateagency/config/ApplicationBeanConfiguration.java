package springmvc.realestateagency.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springmvc.realestateagency.util.HtmlFileReader;
import springmvc.realestateagency.util.HtmlFileReaderImpl;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicationBeanConfiguration {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	
	@Bean
	public Validator validator(){
		return Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	@Bean
	public HtmlFileReader htmlFileReader(){
		return new HtmlFileReaderImpl();
	}
	
}
