package org.softuni.cardealer.service;

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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SupplierServiceImplTest {

    private SupplierService supplierService;

    private ModelMapper modelMapper;

    @Autowired
    private SupplierRepository supplierRepository;

    private SupplierServiceModel supplierFromDb;


    @Before
    public void setUp() throws Exception {
        modelMapper = new ModelMapper();
        supplierService = new SupplierServiceImpl(supplierRepository, modelMapper);
        Supplier supplier = new Supplier("Supplier Name", true);
        supplier = supplierRepository.save(supplier);
        supplierFromDb = modelMapper.map(supplier, SupplierServiceModel.class);
    }

    @Test
    public void supplierService_saveSupplierWithCorrectValues_returnCorrect() {
//        Arrange
        SupplierServiceModel supplierTosave = new SupplierServiceModel("test", false);

//        Act
        supplierTosave = supplierService.saveSupplier(supplierTosave);

//        Assert
        assertTrue(supplierRepository.findById(supplierTosave.getId()).isPresent());
    }

    @Test(expected = Exception.class)
    public void supplierService_saveSupplierWithNullValues_throwException() {
//        Arrange
        SupplierServiceModel supplierTosave = new SupplierServiceModel();

//        Act
        supplierTosave = supplierService.saveSupplier(supplierTosave);
    }

    @Test
    public void supplierService_editSupplierWithCorrectValues_returnCorrect() {
//        Act
        supplierFromDb.setName("changed name");
        supplierFromDb.setImporter(false);
        supplierService.editSupplier(supplierFromDb);
        SupplierServiceModel actualSupplier = modelMapper.map(supplierRepository.findById(supplierFromDb.getId()).get(),
                SupplierServiceModel.class);

//        Assert
       assertEquals(supplierFromDb,actualSupplier);
    }

    @Test(expected = Exception.class)
    public void supplierService_editSupplierWithNullValues_throwException() {
//        Act
        supplierFromDb.setName(null);
        supplierService.editSupplier(supplierFromDb);
    }

    @Test
    public void supplierService_deleteSupplierWithCorrectId_returnCorrect() {
//        Arrange
        String id = supplierFromDb.getId();

//        Act
        supplierService.deleteSupplier(id);

//        Assert
        assertTrue(supplierRepository.findById(id).isEmpty());
    }

    @Test
    public void supplierService_findSupplierWithCorrectId_returnCorrect() {
//        Arrange
        String id = supplierFromDb.getId();

//        Act
        SupplierServiceModel excpectedSupplier = supplierService.findSupplierById(id);
        Optional<Supplier> actualSupplierOptional = supplierRepository.findById(id);

//        Assert
        assertEquals(excpectedSupplier,modelMapper.map(actualSupplierOptional.get(),SupplierServiceModel.class));

    }

    @Test(expected = Exception.class)
    public void supplierService_findSupplierWithUnCorrectId_throwException() {
//        Act
        SupplierServiceModel excpectedSupplier = supplierService.findSupplierById("wrongId");
    }
}