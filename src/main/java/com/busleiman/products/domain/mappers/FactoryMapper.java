package com.busleiman.products.domain.mappers;

import com.busleiman.products.domain.dtos.FactoryDTO;
import com.busleiman.products.domain.dtos.ProductDTO;
import com.busleiman.products.domain.dtos.responses.FactoryResponse;
import com.busleiman.products.domain.dtos.responses.ProductResponse;
import com.busleiman.products.domain.entities.Factory;
import com.busleiman.products.domain.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FactoryMapper {

    FactoryMapper INSTANCE = Mappers.getMapper(FactoryMapper.class);

    Factory FactoryDTOToFactory(FactoryDTO factoryDTO);

    FactoryResponse factoryToFactoryResponse(Factory factory);
}
