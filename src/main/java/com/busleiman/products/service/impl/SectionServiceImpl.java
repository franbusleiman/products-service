package com.busleiman.products.service.impl;

import com.busleiman.products.domain.dtos.SectionDTO;
import com.busleiman.products.domain.dtos.responses.SectionResponse;
import com.busleiman.products.domain.entities.Product;
import com.busleiman.products.domain.entities.Section;
import com.busleiman.products.domain.enums.Edibility;
import com.busleiman.products.domain.mappers.SectionMapper;
import com.busleiman.products.exceptions.NotFoundException;
import com.busleiman.products.persistance.SectionRepository;
import com.busleiman.products.service.SectionService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionServiceImpl implements SectionService{
    
    @Autowired
    private SectionRepository sectionRepository;

    private SectionMapper sectionMapper = SectionMapper.INSTANCE;


    @Override
    public SectionResponse findById(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Resource not found"));

        return sectionMapper.sectionToSectionResponse(section);
    }

    @Override
    public List<SectionResponse> findAll() {

        return sectionRepository.findAll().stream()
                .map(section -> sectionMapper.sectionToSectionResponse(section))
                .collect(Collectors.toList());
    }

    @Override
    public List<SectionResponse> findAllEatables() {
        return sectionRepository.findAll().stream()
                .filter(section -> section.getEdibility().equals(Edibility.EATABLE))
                .map(section -> sectionMapper.sectionToSectionResponse(section))
                .collect(Collectors.toList());
    }

    @Override
    public List<SectionResponse> findAllNonEatables() {
        return sectionRepository.findAll().stream()
                .filter(section -> section.getEdibility().equals(Edibility.NON_EATABLE))
                .map(section -> sectionMapper.sectionToSectionResponse(section))
                .collect(Collectors.toList());
    }

    @Override
    public Long create(SectionDTO sectionDTO) {

        Section section = sectionMapper.SectionDTOtoSection(sectionDTO);

        return sectionRepository.save(section).getId();
    }

    @Override
    public void delete(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Resource not found"));

        sectionRepository.delete(section);
    }

    @Override
    public void update(SectionDTO sectionDTO, Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Resource not found"));

        if(sectionDTO.getEdibility() != null){
            section.setEdibility(sectionDTO.getEdibility());
        }
        if(sectionDTO.getName() != null){
            section.setName(sectionDTO.getName());
        }

        sectionRepository.save(section);
    }
}
