package org.softuni.cardealer.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.repository.PartRepository;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PartsControllerTest {

    private static final String PART_ADD_URL = "/parts/add";
    private static final String PART_EDIT_URL_TEMPL = "/parts/edit/%s";
    private static final String PART_DELETE_URL_TEMPL = "/parts/delete/%s";
    private static final String PART_ALL_URL = "/parts/all";
    private static final String PART_FETCH_URL = "/parts/fetch";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private SupplierRepository supplierRepository;


    @Before
    public void setUp() throws Exception {
        partRepository.deleteAll();
        supplierRepository.deleteAll();
    }

    @Test
    @WithMockUser("spring")
    public void add_saveCorrectPart() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("megaparts");
        supplier.setIsImporter(true);

        supplier = supplierRepository.saveAndFlush(supplier);

        mockMvc
                .perform(post(PART_ADD_URL)
                        .param("name", "Pesho")
                        .param("price", "20.55")
                        .param("supplier", "megaparts")
                );


        Part actual = partRepository.findAll().get(0);
        assertEquals(1, supplierRepository.count());
        assertEquals("Pesho", actual.getName());
        assertEquals(BigDecimal.valueOf(20.55), actual.getPrice());
        assertEquals("megaparts", actual.getSupplier().getName());
    }

    @Test
    @WithMockUser("spring")
    public void add_redirectCorrectView() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("megaparts");
        supplier.setIsImporter(true);

        supplier = supplierRepository.saveAndFlush(supplier);

        mockMvc
                .perform(post(PART_ADD_URL)
                        .param("name", "part1")
                        .param("price", "20.55")
                        .param("supplier", "megaparts")
                )
                .andExpect(redirectedUrl("all"));
    }


    @Test
    @WithMockUser("spring")
    public void edit_changePartProperties() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("megaparts");
        supplier.setIsImporter(true);

        supplier = supplierRepository.saveAndFlush(supplier);

        Part part = new Part();
        part.setName("part1");
        part.setPrice(BigDecimal.valueOf(20.55));
        part.setSupplier(supplier);

        part = partRepository.saveAndFlush(part);

        mockMvc
                .perform(post(String.format(PART_EDIT_URL_TEMPL, part.getId()))
                        .param("name", "part1Changed")
                        .param("price", "21.55")
                        .param("supplier", "megapartsChanged")
                );
        Part actual = partRepository.findAll().get(0);
        assertEquals(1, partRepository.count());
        assertEquals("part1Changed", actual.getName());
        assertEquals(BigDecimal.valueOf(21.55), actual.getPrice());
    }

    @Test
    @WithMockUser("spring")
    public void edit_redirectCorrectView() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("megaparts");
        supplier.setIsImporter(true);

        supplier = supplierRepository.saveAndFlush(supplier);

        Part part = new Part();
        part.setName("part1");
        part.setPrice(BigDecimal.valueOf(20.55));
        part.setSupplier(supplier);

        part = partRepository.saveAndFlush(part);

        mockMvc
                .perform(post(String.format(PART_EDIT_URL_TEMPL, part.getId()))
                        .param("name", "part1Changed")
                        .param("price", "21.55")
                        .param("supplier", "megapartsChanged")
                )
                .andExpect(redirectedUrl("/parts/all"));
    }

    @Test
    @WithMockUser("spring")
    public void delete_removePartFromDb() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("megaparts");
        supplier.setIsImporter(true);

        supplier = supplierRepository.saveAndFlush(supplier);

        Part part = new Part();
        part.setName("part1");
        part.setPrice(BigDecimal.valueOf(20.55));
        part.setSupplier(supplier);

        part = partRepository.saveAndFlush(part);

        mockMvc
                .perform(post(String.format(PART_DELETE_URL_TEMPL,part.getId())));
        assertEquals(0,partRepository.count());
    }

    @Test
    @WithMockUser("spring")
    public void all_returnCorrectView() throws Exception {
        mockMvc
                .perform(get(PART_ALL_URL))
                .andExpect(view().name("all-parts"));
    }

    @Test
    @WithMockUser("spring")
    public void fetch_returnCorrectObjects() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("megaparts");
        supplier.setIsImporter(true);

        supplier = supplierRepository.saveAndFlush(supplier);

        Part part1 = new Part();
        part1.setName("part1");
        part1.setPrice(BigDecimal.valueOf(20.55));
        part1.setSupplier(supplier);

        Part part2 = new Part();
        part2.setName("part2");
        part2.setPrice(BigDecimal.valueOf(55.22));
        part2.setSupplier(supplier);

        List<Part> parts = List.of(part1,part2);
        parts = partRepository.saveAll(parts);

        ModelMapper modelMapper = new ModelMapper();
        List<PartServiceModel> partServiceModels = parts.stream()
                .map(part -> modelMapper.map(part, PartServiceModel.class))
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedResult = objectMapper.writeValueAsString(partServiceModels);

        String actualResult = mockMvc
                .perform(get(PART_FETCH_URL))
                .andReturn().getResponse().getContentAsString();

        assertEquals(expectedResult, actualResult);
    }
}