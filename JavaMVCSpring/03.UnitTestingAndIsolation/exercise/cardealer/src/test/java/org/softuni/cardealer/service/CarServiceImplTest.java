package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.repository.CarRepository;
import org.softuni.cardealer.repository.PartRepository;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarServiceImplTest {

    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    private ModelMapper modelMapper;

    private List<Part> parts;

    @Before
    public void setUp() throws Exception {
        modelMapper = new ModelMapper();
        carService = new CarServiceImpl(carRepository, modelMapper);
        Supplier supplier = new Supplier("Parts Supplier Ltd", true);
        supplierRepository.save(supplier);
        Part part1 = new Part("testPart1", new BigDecimal(50.45), supplier);
        Part part2 = new Part("testPart2", new BigDecimal(120.45), supplier);
        parts = new ArrayList<>(Arrays.asList(part1, part2));
        partRepository.saveAll(parts);
        carRepository.save(new Car("BMW", "M3", 126648L, parts));
    }

    @Test
    public void carService_saveCarWithCorrectValues_returnCorrect() {
        //Arrange
        CarServiceModel carToSave = new CarServiceModel("Audi", "A8", 88000L,
                Arrays.asList(modelMapper.map(parts, PartServiceModel[].class)));

        //Act
        CarServiceModel expectedCar = carService.saveCar(carToSave);
        Optional<Car> actualCarOptional = carRepository.findById(expectedCar.getId());
        CarServiceModel actualCar = null;
        if (actualCarOptional.isPresent()) {
            actualCar = modelMapper.map(actualCarOptional.get(), CarServiceModel.class);
        }

        //Assert
        assertEquals(expectedCar, actualCar);
    }

    @Test(expected = Exception.class)
    public void carService_saveCarWithNullValues_assertNull() {
        //Arrange
        CarServiceModel carToSave = new CarServiceModel();

        //Act
        CarServiceModel savedCar = carService.saveCar(carToSave);

    }

    @Test
    public void carService_editCarWithCorrectValues_returnCorrect() {
        //Arrange
        CarServiceModel carToEdit = modelMapper.map(carRepository.findAll().get(0), CarServiceModel.class);

        //Act
        carToEdit.setMake("Mercedes");
        carToEdit.setModel("S600");
        carToEdit.setTravelledDistance(85000L);
        CarServiceModel expectedCar = carService.editCar(carToEdit);
        CarServiceModel actualCar = null;
        Optional<Car> actualCarOptional = carRepository.findById(carToEdit.getId());
        if (actualCarOptional.isPresent()) {
            actualCar = modelMapper.map(actualCarOptional.get(), CarServiceModel.class);
        }
        //Assert
        assertEquals(expectedCar, actualCar);
    }

    @Test(expected = Exception.class)
    public void carService_editCarWithNullValues_assertNull() {
        //Arrange
        CarServiceModel carToEdit = modelMapper.map(carRepository.findAll().get(0), CarServiceModel.class);

        //Act
        carToEdit.setMake(null);
        carToEdit.setModel(null);
        carToEdit.setTravelledDistance(null);
        carToEdit.setParts(null);
        carService.editCar(carToEdit);
    }

    @Test
    public void carService_deleteCarWithCorrectId_returnCorrect() {
        //Arrange
        CarServiceModel carToDelete = modelMapper.map(carRepository.findAll().get(0), CarServiceModel.class);

        //Act
        carService.deleteCar(carToDelete.getId());
        Optional<Car> deletedCarOptional = carRepository.findById(carToDelete.getId());

        //Assert
        assertTrue(deletedCarOptional.isEmpty());

    }

    @Test(expected = Exception.class)
    public void carService_deleteCarWithUnCorrectId_returnCorrect() {
        //Act
        carService.deleteCar("wrongId");
    }

    @Test
    public void carService_findCarWithCorrectId_returnCorrect() {
        //Arrange
        CarServiceModel carToFind = modelMapper.map(carRepository.findAll().get(0), CarServiceModel.class);
        String carId = carToFind.getId();

        //Act
        CarServiceModel expectedCar = carService.findCarById(carId);
        Optional<Car> car = carRepository.findById(carId);

        //Assert
        assertTrue(car.isPresent());
    }

    @Test(expected = Exception.class)
    public void carService_findCarWithUnCorrectId_throwException() {
        //Act
        CarServiceModel expectedCar = carService.findCarById("wrongId");
    }
}