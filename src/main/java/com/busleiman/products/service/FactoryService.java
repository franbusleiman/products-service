package com.busleiman.products.service;

import com.busleiman.products.domain.dtos.FactoryDTO;
import com.busleiman.products.domain.dtos.responses.FactoryResponse;

import java.util.List;

public interface FactoryService {

    FactoryResponse findById(Long id);

    List<FactoryResponse> findAll();

    List<FactoryResponse> findAllWithMoreThanXProducts(int productQuantity);

    Long create(FactoryDTO factoryDTO);

    void delete(Long id);

    void update(FactoryDTO factoryDTO, Long id);
}
