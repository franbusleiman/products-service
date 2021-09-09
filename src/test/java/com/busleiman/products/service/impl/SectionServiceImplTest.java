package com.busleiman.products.service.impl;

import com.busleiman.products.domain.dtos.SectionDTO;
import com.busleiman.products.domain.dtos.responses.FactoryResponse;
import com.busleiman.products.domain.dtos.responses.SectionResponse;
import com.busleiman.products.domain.entities.Factory;
import com.busleiman.products.domain.entities.Product;
import com.busleiman.products.domain.entities.Section;
import com.busleiman.products.domain.enums.Edibility;
import com.busleiman.products.persistance.SectionRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SectionServiceImplTest {

    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks
    private SectionServiceImpl sectionService;

    private static final String NAME = "name";
    private static final String NAME2 = "name2";
    private static final Edibility EDIBILITY = Edibility.EATABLE;
    private static final Edibility EDIBILITY2 = Edibility.NON_EATABLE;
    private static final Long ID = 1L;
    private static final Long ID2 = 2L;
    private static final Double PRICE = 123.22;
    private static final LocalDate DATE = LocalDate.now();


    @Test
    void findById() {
        when(sectionRepository.findById(anyLong())).thenReturn(Optional.of(getSection()));

        SectionResponse sectionResponse = sectionService.findById(1L);

        verify(sectionRepository, times(1)).findById(anyLong());

        assertEquals(ID, sectionResponse.getId());
        assertEquals(NAME, sectionResponse.getName());
        assertEquals(EDIBILITY, sectionResponse.getEdibility());

    }

    @Test
    void findAll() {
        when(sectionRepository.findAll()).thenReturn(Collections.singletonList(getSection()));

        List<SectionResponse> sectionList = sectionService.findAll();

        verify(sectionRepository, times(1)).findAll();

        assertEquals(1, sectionList.size());
        assertEquals(1L, sectionList.get(0).getId());
    }


    @Test
    void findAllEatables() {
        List<Section> sectionList = new ArrayList<>();
        sectionList.add(getSection());
        sectionList.add(getSection2());

        when(sectionRepository.findAll()).thenReturn(sectionList);

        List<SectionResponse> sectionResponses = sectionService.findAllEatables();

        verify(sectionRepository, times(1)).findAll();

        assertEquals(1, sectionResponses.size());
        assertEquals(ID, sectionResponses.get(0).getId());
    }

    @Test
    void findAllNonEatables() {
        List<Section> sectionList = new ArrayList<>();
        sectionList.add(getSection());
        sectionList.add(getSection2());

        when(sectionRepository.findAll()).thenReturn(sectionList);

        List<SectionResponse> sectionResponses = sectionService.findAllNonEatables();

        verify(sectionRepository, times(1)).findAll();

        assertEquals(1, sectionResponses.size());
        assertEquals(ID2, sectionResponses.get(0).getId());
    }

    @Test
    void create() {
        when(sectionRepository.save(any())).thenReturn(getSection());

        ArgumentCaptor<Section> captor = ArgumentCaptor.forClass(Section.class);

        Long sectionID = sectionService.create(getSectionDTO());

        verify(sectionRepository).save(captor.capture());

        Section section = captor.getValue();

        assertNotNull(sectionID);
        assertEquals(NAME, section.getName());
        assertEquals(EDIBILITY, section.getEdibility());
    }

    @Test
    void delete() {
        when(sectionRepository.findById(anyLong())).thenReturn(Optional.of(getSection()));

        sectionService.delete(1L);

        verify(sectionRepository, times(1)).delete(any());
    }

    @Test
    void update() {
        when(sectionRepository.findById(anyLong())).thenReturn(Optional.of(getSection()));

        ArgumentCaptor<Section> captor = ArgumentCaptor.forClass(Section.class);

        sectionService.update(getSectionDTO2(), 1L);

        verify(sectionRepository, times(1)).findById(anyLong());
        verify(sectionRepository).save(captor.capture());

        Section section = captor.getValue();

        assertEquals(NAME2, section.getName());
        assertEquals(EDIBILITY2, section.getEdibility());
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
                .name(NAME)
                .edibility(EDIBILITY2)
                .build();
    }

    private SectionDTO getSectionDTO() {
        return SectionDTO
                .builder()
                .edibility(EDIBILITY)
                .name(NAME)
                .build();
    }

    private SectionDTO getSectionDTO2() {
        return SectionDTO
                .builder()
                .edibility(EDIBILITY2)
                .name(NAME2)
                .build();
    }

    private Product getProduct() {
        return Product.builder()
                .id(ID)
                .createAt(DATE)
                .price(PRICE)
                .name(NAME)
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
}