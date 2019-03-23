package org.softuni.cardealer.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SuppliersControllerTest {

    private static final String SUPPLIER_ADD_URL = "/suppliers/add";
    private static final String SUPPLIER_EDIT_URL_TEMPL = "/suppliers/edit/%s";
    private static final String SUPPLIER_DELETE_URL_TEMPL = "/suppliers/delete/%s";
    private static final String SUPPLIER_ALL_URL = "/suppliers/all";
    private static final String SUPPLIER_FETCH_URL = "/suppliers/fetch";


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private SupplierRepository supplierRepository;


    @Before
    public void setUp() throws Exception {
        supplierRepository.deleteAll();
    }

    @Test
    @WithMockUser("spring")
    public void add_saveCorrectSupplier() throws Exception {
        mockMvc
                .perform(post(SUPPLIER_ADD_URL)
                        .param("name", "Pesho")
                        .param("isImporter", "true")
                );


        Supplier actual = supplierRepository.findAll().get(0);
        assertEquals(1, supplierRepository.count());
        assertEquals("Pesho", actual.getName());
        assertTrue(actual.getIsImporter());
    }

    @Test
    @WithMockUser("spring")
    public void add_redirectCorrectView() throws Exception {
        mockMvc
                .perform(post(SUPPLIER_ADD_URL)
                        .param("name", "Pesho")
                        .param("isImporter", "true")
                )
                .andExpect(redirectedUrl("all"));
    }

    @Test
    @WithMockUser("spring")
    public void edit_changeSupplierProperties() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("Pesho");
        supplier.setIsImporter(true);

        supplier = supplierRepository.saveAndFlush(supplier);

        mockMvc
                .perform(post(String.format(SUPPLIER_EDIT_URL_TEMPL, supplier.getId()))
                        .param("name", "Gosho")
                        .param("isImporter", "false")
                );

        Supplier actualSupplier = supplierRepository.findById(supplier.getId()).get();

        assertEquals(1, supplierRepository.count());
        assertEquals("Gosho", actualSupplier.getName());
        assertFalse(actualSupplier.getIsImporter());
    }

    @Test
    @WithMockUser("spring")
    public void edit_redirectCorrectView() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("Pesho");
        supplier.setIsImporter(true);

        supplier = supplierRepository.saveAndFlush(supplier);

        mockMvc
                .perform(post(String.format(SUPPLIER_EDIT_URL_TEMPL, supplier.getId()))
                        .param("name", "Gosho")
                        .param("isImporter", "false")
                )
                .andExpect(redirectedUrl("/suppliers/all"));
    }

    @Test
    @WithMockUser("spring")
    public void delete_removeSupplierFromDb() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("Pesho");
        supplier.setIsImporter(true);

        supplier = supplierRepository.saveAndFlush(supplier);

        mockMvc
                .perform(post(String.format(SUPPLIER_DELETE_URL_TEMPL, supplier.getId())));

        assertEquals(0, supplierRepository.count());
    }

    @Test
    @WithMockUser("spring")
    public void all_returnCorrectView() throws Exception {
        mockMvc
                .perform(get(SUPPLIER_ALL_URL))
                .andExpect(view().name("all-suppliers"));
    }

    @Test
    @WithMockUser("spring")
    public void fetch_returnCorrectObjects() throws Exception {
        Supplier supplier1 = new Supplier();
        supplier1.setName("Pesho");
        supplier1.setIsImporter(true);

        Supplier supplier2 = new Supplier();
        supplier2.setName("Gosho");
        supplier2.setIsImporter(false);

        List<Supplier> suppliers = List.of(supplier1, supplier2);
        suppliers = supplierRepository.saveAll(suppliers);

        ModelMapper modelMapper = new ModelMapper();
        List<SupplierServiceModel> supplierServiceModels = suppliers.stream()
                .map(supplier -> modelMapper.map(supplier, SupplierServiceModel.class))
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedResult = objectMapper.writeValueAsString(supplierServiceModels);

        String actualResult = mockMvc
                .perform(get(SUPPLIER_FETCH_URL))
                .andReturn().getResponse().getContentAsString();

        assertEquals(expectedResult, actualResult);
    }
}