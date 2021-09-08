package com.busleiman.products.domain.mappers;

import com.busleiman.products.domain.dtos.SectionDTO;
import com.busleiman.products.domain.dtos.responses.SectionResponse;
import com.busleiman.products.domain.entities.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SectionMapper {

    SectionMapper INSTANCE = Mappers.getMapper(SectionMapper.class);


    Section SectionDTOtoSection(SectionDTO sectionDTO);

    SectionResponse sectionToSectionResponse(Section section);
}
