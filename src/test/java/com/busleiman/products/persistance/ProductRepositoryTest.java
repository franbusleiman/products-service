package com.busleiman.products.persistance;

import com.busleiman.products.domain.entities.Factory;
import com.busleiman.products.domain.entities.Product;
import com.busleiman.products.domain.entities.Section;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    FactoryRepository factoryRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Test
    void cascadePersistTest(){

         Factory factory = factoryRepository.save(getFactory());
         Section section = sectionRepository.save(getSection());

        Product product = getProduct();

        product.setFactory(factory);
        product.setSection(section);

        factory.setProductList(Collections.singletonList(product));
        section.setProductList(Collections.singletonList(product));

        productRepository.save(product);

        Factory factory2  = factoryRepository.findAll().get(0);
        Section section2 = sectionRepository.findAll().get(0);

        assertEquals("my product", factory2.getProductList().get(0).getName());
        assertEquals("my product", section2.getProductList().get(0).getName());
        assertNotNull(factory2.getProductList().get(0).getId());
        assertNotNull(section2.getProductList().get(0).getId());
    }


    private Product getProduct(){
        return Product.builder()
                .name("my product")
                .build();
    }

    private Factory getFactory(){
        return Factory.builder()
                .name("my factory")
                .build();
    }

    private Section getSection(){
        return Section.builder()
                .name("my section")
                .build();
    }

}