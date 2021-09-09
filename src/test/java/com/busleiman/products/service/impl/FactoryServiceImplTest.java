package com.busleiman.products.service.impl;

import com.busleiman.products.domain.dtos.FactoryDTO;
import com.busleiman.products.domain.dtos.responses.FactoryResponse;
import com.busleiman.products.domain.dtos.responses.ProductResponse;
import com.busleiman.products.domain.entities.Factory;
import com.busleiman.products.domain.entities.Product;
import com.busleiman.products.domain.entities.Section;
import com.busleiman.products.persistance.FactoryRepository;
import com.sun.org.apache.xpath.internal.Arg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FactoryServiceImplTest {

    @Mock
    private FactoryRepository factoryRepository;

    @InjectMocks
    private FactoryServiceImpl factoryService;

    private static final Long ID = 1L;
    private static final String NAME = "name";
    private static final String NAME2 = "name2";
    private static final String ADDRESS = "Address";
    private static final String ADDRESS2 = "Address";
    private static final String EMAIL = "email@gmail.com";
    private static final String EMAIL2 = "email@gmail.com";
    private static final String PHONE_NUMBER = "+35221412";
    private static final String PHONE_NUMBER2 = "+35221412";
    private static final Double PRICE = 123.22;
    private static final LocalDate DATE = LocalDate.now();

    @Test
    void findById() {
        when(factoryRepository.findById(anyLong())).thenReturn(Optional.of(getFactory()));

        FactoryResponse factoryResponse = factoryService.findById(1L);

        verify(factoryRepository, times(1)).findById(anyLong());

        assertEquals(ID, factoryResponse.getId());
        assertEquals(NAME, factoryResponse.getName());
        assertEquals(ADDRESS, factoryResponse.getAddress());
        assertEquals(EMAIL, factoryResponse.getEmail());
        assertEquals(PHONE_NUMBER, factoryResponse.getPhoneNumber());
    }

    @Test
    void findAll() {
        when(factoryRepository.findAll()).thenReturn(Collections.singletonList(getFactory()));

        List<FactoryResponse> factoryList = factoryService.findAll();

        verify(factoryRepository, times(1)).findAll();

        assertEquals(1, factoryList.size());
        assertEquals(1L, factoryList.get(0).getId());
    }

    @Test
    void findAllWithMoreThanXProducts() {

        Product product1 = getProduct();
        Product product2 = getProduct();
        product2.setId(2L);
        Product product3 = getProduct();
        product3.setId(3L);

        Factory factory1 = getFactory();
        factory1.setProductList(new ArrayList<>());
        factory1.getProductList().add(product1);
        factory1.getProductList().add(product2);
        factory1.getProductList().add(product3);

        Factory factory2 = getFactory();
        factory2.setId(2L);

        List<Factory> factoryList = new ArrayList<>();
        factoryList.add(factory1);
        factoryList.add(factory2);

        when(factoryRepository.findAll()).thenReturn(factoryList);

        List<FactoryResponse> factoryList1 = factoryService.findAllWithMoreThanXProducts(2);

        verify(factoryRepository, times(1)).findAll();

        assertEquals(1, factoryList1.size());
        assertEquals(ID, factoryList1.get(0).getId());
    }

    @Test
    void create() {
        when(factoryRepository.save(any())).thenReturn(getFactory());

        ArgumentCaptor<Factory> captor = ArgumentCaptor.forClass(Factory.class);

        Long factoryID = factoryService.create(getFactoryDTO());

        verify(factoryRepository).save(captor.capture());

        Factory factory = captor.getValue();

        assertNotNull(factoryID);
        assertEquals(NAME, factory.getName());
        assertEquals(ADDRESS, factory.getAddress());
        assertEquals(EMAIL, factory.getEmail());
        assertEquals(PHONE_NUMBER, factory.getPhoneNumber());
    }

    @Test
    void delete() {
        when(factoryRepository.findById(anyLong())).thenReturn(Optional.of(getFactory()));

        factoryService.delete(1L);

        verify(factoryRepository, times(1)).delete(any());
    }

    @Test
    void update() {
        when(factoryRepository.findById(anyLong())).thenReturn(Optional.of(getFactory()));

        ArgumentCaptor<Factory> captor = ArgumentCaptor.forClass(Factory.class);

        factoryService.update(getFactoryDTO2(), 1L);

        verify(factoryRepository, times(1)).findById(anyLong());
        verify(factoryRepository).save(captor.capture());

        Factory factory = captor.getValue();

        assertEquals(NAME2, factory.getName());
        assertEquals(ADDRESS2, factory.getAddress());
        assertEquals(EMAIL2, factory.getEmail());
        assertEquals(PHONE_NUMBER2, factory.getPhoneNumber());

    }

    private FactoryDTO getFactoryDTO() {
        return FactoryDTO.builder()
                .address(ADDRESS)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .name(NAME)
                .build();
    }

    private FactoryDTO getFactoryDTO2() {
        return FactoryDTO.builder()
                .address(ADDRESS2)
                .email(EMAIL2)
                .phoneNumber(PHONE_NUMBER2)
                .name(NAME2)
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
    private Product getProduct() {
        return Product.builder()
                .id(ID)
                .createAt(DATE)
                .price(PRICE)
                .name(NAME)
                .build();
    }

}