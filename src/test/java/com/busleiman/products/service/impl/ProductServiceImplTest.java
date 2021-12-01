package com.busleiman.products.service.impl;

import com.busleiman.products.domain.dtos.ProductDTO;
import com.busleiman.products.domain.dtos.responses.ProductResponse;
import com.busleiman.products.domain.entities.Factory;
import com.busleiman.products.domain.entities.Product;
import com.busleiman.products.domain.entities.Section;
import com.busleiman.products.domain.enums.Edibility;
import com.busleiman.products.exceptions.NotFoundException;
import com.busleiman.products.persistance.FactoryRepository;
import com.busleiman.products.persistance.ProductRepository;
import com.busleiman.products.persistance.SectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private FactoryRepository factoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;


    private static final Long ID = 1L;
    private static final Long ID2 = 2L;
    private static final String NAME = "name";
    private static final Double PRICE = 123.22;
    private static final String NAME2 = "name2";
    private static final Double PRICE2 = 223.22;
    private static final String NAME3 = "a123";
    private static final Double PRICE3 = 29.00;
    private static final LocalDate DATE = LocalDate.now();
    private static final String ADDRESS = "Address";
    private static final String EMAIL = "email@gmail.com";
    private static final String PHONE_NUMBER = "+35221412";
    private static final Edibility EDIBILITY = Edibility.EATABLE;
    private static final Edibility EDIBILITY2 = Edibility.NON_EATABLE;


    @Test
    void findById() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(getProduct(NAME, PRICE)));

        ProductResponse productResponse = productService.findById(1L);

        verify(productRepository, times(1)).findById(anyLong());

        assertEquals(ID, productResponse.getId());
        assertEquals(NAME, productResponse.getName());
        assertEquals(ID, productResponse.getFactoryId());
        assertEquals(ID, productResponse.getSectionId());
    }

    @Test
    void findByIdNotFound() {
        when(
                productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.findById(1L));
    }

    @Test
    void findAll() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(getProduct(NAME, PRICE)));

        List<ProductResponse> productResponses = productService.findAll();

        verify(productRepository, times(1)).findAll();

        assertEquals(1, productResponses.size());
        assertEquals(ID, productResponses.get(0).getId());
    }

    @Test
    void findAllOrderedByName() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(getProduct(NAME, PRICE), getProduct(NAME2, PRICE2), getProduct(NAME3, PRICE3)));

        List<ProductResponse> productResponses = productService.findAllOrderedByName();

        verify(productRepository, times(1)).findAll();

        assertEquals(3, productResponses.size());
        assertEquals(NAME3, productResponses.get(0).getName());
        assertEquals(NAME, productResponses.get(1).getName());
        assertEquals(NAME2, productResponses.get(2).getName());
    }

    @Test
    void findAllOrderedByPrice() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(getProduct(NAME, PRICE), getProduct(NAME2, PRICE2), getProduct(NAME3, PRICE3)));

        List<ProductResponse> productResponses = productService.findAllOrderedByPrice();

        verify(productRepository, times(1)).findAll();

        assertEquals(3, productResponses.size());
        assertEquals(PRICE3, productResponses.get(0).getPrice());
        assertEquals(PRICE, productResponses.get(1).getPrice());
        assertEquals(PRICE2, productResponses.get(2).getPrice());
    }

    @Test
    void create() {
        when(sectionRepository.findById(anyLong())).thenReturn(Optional.of(getSection()));
        when(factoryRepository.findById(anyLong())).thenReturn(Optional.of(getFactory()));
        when(productRepository.save(any())).thenReturn(getProduct(NAME, PRICE));

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        Long productCreatedId = productService.create(getProductDTO());

        verify(sectionRepository, times(1)).findById(any());
        verify(factoryRepository, times(1)).findById(any());
        verify(productRepository).save(captor.capture());

        Product product = captor.getValue();

        assertNotNull(productCreatedId);
        assertEquals(NAME, product.getName());
        assertEquals(EDIBILITY, product.getSection().getEdibility());
        assertEquals(PRICE, product.getPrice());
        assertEquals(EMAIL, product.getFactory().getEmail());
        assertEquals(1, product.getSection().getProductList().size());
        assertEquals(1, product.getFactory().getProductList().size());
    }

    @Test
    void delete() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(getProduct(NAME, PRICE)));

        productService.delete(1L);

        verify(productRepository, times(1)).delete(any());
    }

    @Test
    void update() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(getProduct(NAME, PRICE)));
        when(sectionRepository.findById(anyLong())).thenReturn(Optional.of(getSection2()));

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        productService.update(getProductDTO2(), ID);

        verify(productRepository, times(1)).findById(anyLong());
        verify(sectionRepository, times(1)).findById(anyLong());
        verify(sectionRepository).save(any());
        verify(productRepository).save(captor.capture());

        Product product = captor.getValue();

        assertEquals(NAME2, product.getName());
        assertEquals(PRICE2, product.getPrice());
        assertEquals(NAME2, product.getSection().getName());
        assertEquals(EDIBILITY2, product.getSection().getEdibility());
        assertEquals(1, product.getSection().getProductList().size());
    }

    private ProductDTO getProductDTO2() {
        return ProductDTO.builder()
                .name(NAME2)
                .price(PRICE2)
                .sectionId(ID2)
                .build();
    }

    private ProductDTO getProductDTO() {
        return ProductDTO.builder()
                .name(NAME)
                .price(PRICE)
                .sectionId(ID)
                .factoryId(ID)
                .build();
    }

    private Product getProduct(String name, Double price) {
        return Product.builder()
                .id(ID)
                .createAt(DATE)
                .price(price)
                .name(name)
                .factory(Factory.builder()
                        .id(ID)
                        .address(ADDRESS)
                        .email(EMAIL)
                        .phoneNumber(PHONE_NUMBER)
                        .name(NAME)
                        .build())
                .section(Section.builder()
                        .id(ID)
                        .name(NAME)
                        .productList(Collections.singletonList(Product.builder()
                                .id(ID)
                                .createAt(DATE)
                                .price(PRICE)
                                .name(NAME)
                                .build()))
                        .edibility(EDIBILITY)
                        .build())
                .build();
    }

    private Factory getFactory() {
        return Factory.builder()
                .id(ID)
                .address(ADDRESS)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .name(NAME)
                .build();
    }

    private Section getSection() {
        return Section.builder()
                .id(ID)
                .name(NAME)
                .edibility(EDIBILITY)
                .build();
    }

    private Section getSection2() {
        return Section.builder()
                .id(ID2)
                .name(NAME2)
                .edibility(EDIBILITY2)
                .build();
    }

}
