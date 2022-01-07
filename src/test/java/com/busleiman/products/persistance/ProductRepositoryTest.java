package com.busleiman.products.persistance;

import com.busleiman.products.domain.entities.Factory;
import com.busleiman.products.domain.entities.Product;
import com.busleiman.products.domain.entities.Section;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    FactoryRepository factoryRepository;


    Factory factoryA;
    Section sectionA;
    Section sectionB;
    Product productA;
    Product productB;
    Product productC;
    Product productD;

    List<Product> productList;

    @BeforeEach
    public void init() {

        factoryA = factoryRepository.save(new Factory("factoryA"));

        sectionA = sectionRepository.save(new Section("sectionA"));

        sectionB = sectionRepository.save(new Section("sectionB"));

        productA = Product.builder()
                .name("productA")
                .section(sectionA)
                .factory(factoryA)
                .build();

        productB = Product.builder()
                .name("productB")
                .section(sectionA)
                .factory(factoryA)
                .build();

        productC = Product.builder()
                .name("productC")
                .section(sectionB)
                .factory(factoryA)
                .build();

        productD = Product.builder()
                .name("productD")
                .section(sectionB)
                .factory(factoryA)
                .build();

        productList = Arrays.asList(productA, productB, productC, productD);
    }

    @AfterEach
    public void teardown() {
        productRepository.deleteAll();
        sectionRepository.deleteAll();
        factoryRepository.deleteAll();
    }

    @Test
    public void shouldNotAutomaticallySetTheOneToManySide() {

        productRepository.saveAll(productList);

        Section sectionASaved = sectionRepository.findById(sectionA.getId()).get();

        assertNull(sectionASaved.getProductList());

        assertEquals(sectionA.getId(), productA.getSection().getId());
    }

    @Test
    public void cascadeShouldPersistTheProductOnTheOneToManySide() {

        sectionA.setProductList(Arrays.asList(productA, productB));
        sectionB.setProductList(Arrays.asList(productC, productD));

        productRepository.saveAll(productList);

        Product productA1 = productRepository.findByName("productA").get();
        Product productB1 = productRepository.findByName("productB").get();


        Section sectionASaved = sectionRepository.findById(sectionA.getId()).get();

        assertEquals(productA1.getId(), sectionASaved.getProductList().get(0).getId());
        assertEquals(productB1.getId(), sectionASaved.getProductList().get(1).getId());

    }

    @Test
    public void cascadeTypeRemoveShouldDeleteAllProducts() {
        factoryA.setProductList(Arrays.asList(productA, productB, productC, productD));

        productRepository.saveAll(productList);

        factoryRepository.deleteById(factoryA.getId());

        assertEquals(0, productRepository.findAll().size());
    }
}