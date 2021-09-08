package com.busleiman.products.service.impl;

import com.busleiman.products.domain.dtos.FactoryDTO;
import com.busleiman.products.domain.dtos.responses.FactoryResponse;
import com.busleiman.products.domain.entities.Factory;
import com.busleiman.products.domain.mappers.FactoryMapper;
import com.busleiman.products.exceptions.NotFoundException;
import com.busleiman.products.persistance.FactoryRepository;
import com.busleiman.products.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FactoryServiceImpl implements FactoryService {

    @Autowired
    private FactoryRepository factoryRepository;

    private FactoryMapper factoryMapper = FactoryMapper.INSTANCE;


    @Override
    public FactoryResponse findById(Long id) {
        Factory factory = factoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Resource not found"));

        return factoryMapper.factoryToFactoryResponse(factory);
    }

    @Override
    public List<FactoryResponse> findAll() {
        List<Factory> factoryList = (List<Factory>) factoryRepository.findAll();

        return factoryList.stream()
                .map(factory -> factoryMapper.factoryToFactoryResponse(factory))
                .collect(Collectors.toList());
    }

    @Override
    public Long create(FactoryDTO factoryDTO) {

        Factory factory = factoryMapper.FactoryDTOToFactory(factoryDTO);

        return factoryRepository.save(factory).getId();
    }

    @Override
    public void delete(Long id) {
        Factory factory = factoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Resource not found"));

        factoryRepository.delete(factory);
    }

    @Override
    public void update(FactoryDTO factoryDTO, Long id) {
        Factory factory = factoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Resource not found"));

        if (factoryDTO.getName() != null) {
            factory.setName(factoryDTO.getName());
        }
        if (factoryDTO.getAddress() != null) {
            factory.setAddress(factoryDTO.getAddress());
        }
        if (factoryDTO.getEmail() != null) {
            factory.setEmail(factoryDTO.getEmail());
        }
        if (factoryDTO.getPhoneNumber() != null) {
            factory.setPhoneNumber(factoryDTO.getPhoneNumber());
        }

        factoryRepository.save(factory);
    }
}
