package org.softuni.cardealer.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.PartRepository;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PartServiceImplTest {

    private PartService partService;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    private ModelMapper modelMapper;

    private PartServiceModel partFromDb;

    private Supplier supplier;


    @Before
    public void setUp() throws Exception {
        modelMapper = new ModelMapper();
        partService = new PartServiceImpl(partRepository, modelMapper);
        supplier = new Supplier("Parts Supplier Ltd", true);
        supplierRepository.save(supplier);
        Part dbPart = new Part("testPart1", new BigDecimal(50.45), supplier);
        partRepository.save(dbPart);
        partFromDb = modelMapper.map(dbPart, PartServiceModel.class);
    }

    @Test
    public void partService_savePartWithCorrectValues_returnCorrect() {
//        Arrange
        PartServiceModel partToSave = new PartServiceModel("testPart2", new BigDecimal(120.45),
                modelMapper.map(supplier, SupplierServiceModel.class));

//        Act
        PartServiceModel savedPart = partService.savePart(partToSave);

//        Assert
        assertTrue(partRepository.findById(savedPart.getId()).isPresent());
    }

    @Test(expected = Exception.class)
    public void partService_savePartWithNullValues_throwException() {
//        Arrange
        PartServiceModel partToSave = new PartServiceModel();

//        Act
        PartServiceModel savedPart = partService.savePart(partToSave);
    }


    @Test
    public void partService_editPartWithCorrectValues_returnCorrect() {
//        Act
        partFromDb.setName("new name");
        partFromDb.setPrice(BigDecimal.TEN);
        partFromDb = partService.editPart(partFromDb);
        PartServiceModel actualPart = modelMapper.map(partRepository.findById(partFromDb.getId()).get(), PartServiceModel.class);
//        Assert
        assertEquals(partFromDb, actualPart);
    }

    @Test(expected = Exception.class)
    public void partService_editPartWithNullValues_throwException() {
        partFromDb.setName(null);
        partFromDb.setPrice(null);
        partFromDb.setSupplier(null);
        partFromDb = partService.editPart(partFromDb);
    }


    @Test
    public void partService_deletePartByCorrectId_returnCorrect() {
//        Arrange
        String id = partFromDb.getId();

//        Act
        partService.deletePart(id);

//        Assert
        assertTrue(partRepository.findById(id).isEmpty());
    }

    @Test(expected = Exception.class)
    public void partService_deletePartByUnCorrectId_throwException() {
//        Act
        partService.deletePart("wrongId");
    }

    @Test
    public void partService_findPartByCorrectId_returnCorrect() {
//        Arrange
        String id = partFromDb.getId();

//        Act
        PartServiceModel expectedPart = partService.findPartById(id);

//        Assert
        assertNotNull(expectedPart);
    }

    @Test(expected = Exception.class)
    public void partService_findPartByUnCorrectId_throwException() {
//        Act
        PartServiceModel expectedPart = partService.findPartById("wrongId");
    }

}