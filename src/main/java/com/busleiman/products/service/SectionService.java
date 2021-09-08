package com.busleiman.products.service;

import com.busleiman.products.domain.dtos.ProductDTO;
import com.busleiman.products.domain.dtos.SectionDTO;
import com.busleiman.products.domain.dtos.responses.ProductResponse;
import com.busleiman.products.domain.dtos.responses.SectionResponse;

import java.util.List;

public interface SectionService {

    SectionResponse findById(Long id);

    List<SectionResponse> findAll();

    Long create(SectionDTO sectionDTO);

    void delete(Long id);

    void update(SectionDTO sectionDTO, Long id);
}
