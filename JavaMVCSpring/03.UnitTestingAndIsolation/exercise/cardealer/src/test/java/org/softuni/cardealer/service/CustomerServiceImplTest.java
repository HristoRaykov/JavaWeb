package org.softuni.cardealer.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.domain.models.service.CustomerServiceModel;
import org.softuni.cardealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerServiceImplTest {

    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    private ModelMapper modelMapper;


    @Before
    public void setUp() throws Exception {
        modelMapper = new ModelMapper();
        customerService = new CustomerServiceImpl(customerRepository, modelMapper);
        Customer customer = new Customer("Pesho", LocalDate.now(), true);
        customerRepository.save(customer);
    }

    @Test
    public void customerService_saveCustomerWithCorrectValues_returnCorrect() {
//        Arrange
        CustomerServiceModel customerToSave = new CustomerServiceModel("Gosho", LocalDate.now(), false);

//        Act
        CustomerServiceModel saveCustomer = customerService.saveCustomer(customerToSave);

//        Assert
        assertNotNull(saveCustomer.getId());

    }

    @Test(expected = Exception.class)
    public void customerService_saveCustomerWithNullValues_throwException() {
//        Act
        customerService.saveCustomer(new CustomerServiceModel());
    }

    @Test
    public void customerService_editCustomerWithCorrectValues_returnCorrect() {
//        Arrange
        CustomerServiceModel customerToEdit = modelMapper.map(customerRepository.findAll().get(0),CustomerServiceModel.class);

//        Act
        CustomerServiceModel expectedCustomer = customerToEdit;
        expectedCustomer.setBirthDate(LocalDate.of(2000,1,1));
        expectedCustomer.setName("PeshoChanged");
        expectedCustomer.setYoungDriver(false);
        CustomerServiceModel actualCustomer = customerService.editCustomer(expectedCustomer);

//        Assert
        assertEquals(expectedCustomer,actualCustomer);
    }


    @Test
    public void customerService_deleteCustomerWithCorrectId_returnCorrect() {
//        Arrange
        CustomerServiceModel customerToDelete = modelMapper.map(customerRepository.findAll().get(0),CustomerServiceModel.class);

//        Act
        customerService.deleteCustomer(customerToDelete.getId());

//        Assert
        assertTrue(customerRepository.findById(customerToDelete.getId()).isEmpty());
    }

    @Test
    public void customerService_findCustomerWithCorrectId_returnCorrect() {
//        Arrange
        String customerId = customerRepository.findAll().get(0).getId();

//        Act
        CustomerServiceModel expectedCustomer = customerService.findCustomerById(customerId);
        CustomerServiceModel actualCustomer = modelMapper.map(customerRepository.findById(customerId).get(),CustomerServiceModel.class);

//        Assert
        assertEquals(expectedCustomer,actualCustomer);
    }

    @Test(expected = Exception.class)
    public void customerService_findCustomerWithWrongId_throwException() {
//        Assert
        customerService.findCustomerById("wrongId");
    }

}