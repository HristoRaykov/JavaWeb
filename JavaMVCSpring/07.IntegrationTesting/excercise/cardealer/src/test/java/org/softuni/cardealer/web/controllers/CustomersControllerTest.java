package org.softuni.cardealer.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomersControllerTest {

    private static final String CUSTOMER_ADD_URL = "/customers/add";
    private static final String CUSTOMERS_ALL_URL = "/customers/all";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;


    @Before
    public void setUp() throws Exception {
        customerRepository.deleteAll();
    }

    @Test
    @WithMockUser("spring")
    public void add_saveCorrectCustomer() throws Exception {
        mockMvc
                .perform(post(CUSTOMER_ADD_URL)
                        .param("name", "Pesho")
                        .param("birthDate", "2015-12-31")
                );


        Customer actual = customerRepository.findAll().get(0);


        assertEquals("Pesho", actual.getName());
        assertEquals("2015-12-31", actual.getBirthDate().toString());
    }

    @Test
    @WithMockUser("spring")
    public void add_redirectCorrecctView() throws Exception {
        mockMvc
                .perform(post(CUSTOMER_ADD_URL)
                        .param("name", "Pesho")
                        .param("birthDate", "2015-12-31")
                )
                .andExpect(redirectedUrl("all"));

    }

    @Test
    @WithMockUser("spring")
    public void allCustomers() throws Exception {
        mockMvc
                .perform(get(CUSTOMERS_ALL_URL))
                .andExpect(view().name("all-customers"));
    }
}