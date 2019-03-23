package org.softuni.cardealer.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.repository.CarRepository;
import org.softuni.cardealer.repository.CustomerRepository;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarsControllerTest {

    private static final String CAR_ADD_URL = "/cars/add";
    private static final String CAR_EDIT_URL_TEMPL = "/cars/edit/%s";
    private static final String CAR_DELETE_URL_TEMPL = "/cars/delete/%s";
    private static final String CARS_ALL_URL = "/cars/all";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    private Car dbCar;

    private List<Part> parts;


    @Before
    public void setUp() throws Exception {
        carRepository.deleteAll();
        partRepository.deleteAll();
    }

    private void initParts() {
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

        parts = List.of(part1, part2);
        parts = partRepository.saveAll(parts);
    }

    private void initCar() {
        initParts();
        Car car = new Car();
        car.setMake("Audi");
        car.setModel("A6");
        car.setTravelledDistance(120000L);
        car.setParts(parts);

        dbCar = carRepository.saveAndFlush(car);
    }


    @Test
    @WithMockUser("spring")
    public void add_saveCorrectCar() throws Exception {
        initParts();
        mockMvc
                .perform(post(CAR_ADD_URL)
                        .param("make", "Audi")
                        .param("model", "A6")
                        .param("travelledDistance", "120000"));

        Car actual = carRepository.findAll().get(0);
        assertEquals("Audi", actual.getMake());
        assertEquals("A6", actual.getModel());
        assertEquals(Long.valueOf(120000), actual.getTravelledDistance());
    }

    @Test
    @WithMockUser("spring")
    public void add_redirectCorrectView() throws Exception {
        mockMvc
                .perform(post(CAR_ADD_URL)
                        .param("make", "Audi")
                        .param("model", "A6")
                        .param("travelledDistance", "120000"))
                .andExpect(redirectedUrl("all"));
    }

    @Test
    @WithMockUser("spring")
    public void edit_changeCarProperties() throws Exception {
        initCar();
        mockMvc
                .perform(post(String.format(CAR_EDIT_URL_TEMPL, dbCar.getId()))
                        .param("make", "BMW")
                        .param("model", "X6")
                        .param("travelledDistance", "144000"));
        Car actual = carRepository.findAll().get(0);
        assertEquals("BMW", actual.getMake());
        assertEquals("X6", actual.getModel());
        assertEquals(Long.valueOf(144000), actual.getTravelledDistance());
    }

    @Test
    @WithMockUser("spring")
    public void edit_redirectCorrectView() throws Exception {
        initCar();
        mockMvc
                .perform(post(String.format(CAR_EDIT_URL_TEMPL, dbCar.getId()))
                        .param("make", "BMW")
                        .param("model", "X6")
                        .param("travelledDistance", "144000"))
                .andExpect(redirectedUrl("/cars/all"));
    }

    @Test
    @WithMockUser("spring")
    public void delete_removeCarFromDb() throws Exception {
        initCar();
        mockMvc
                .perform(post(String.format(CAR_DELETE_URL_TEMPL, dbCar.getId())));
        assertEquals(0,carRepository.count());
    }

    @Test
    @WithMockUser("spring")
    public void allCars() throws Exception {
        mockMvc
                .perform(get(CARS_ALL_URL))
                .andExpect(view().name("all-cars"));
    }
}