package com.busleiman.products.domain.mappers;

import com.busleiman.products.domain.dtos.ProductDTO;
import com.busleiman.products.domain.dtos.responses.ProductResponse;
import com.busleiman.products.domain.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", ignore = true)
    Product ProductDTOtoProduct(ProductDTO productDTO);

    @Mapping(target = "factoryId", source = "factory.id")
    @Mapping(target = "sectionId", source = "section.id")
    ProductResponse productToProductResponse(Product product);
}
