package com.busleiman.products.service;

import com.busleiman.products.domain.dtos.ProductDTO;
import com.busleiman.products.domain.dtos.responses.ProductResponse;
import com.busleiman.products.domain.entities.Product;

import java.util.List;

public interface ProductService {

     ProductResponse findById(Long id);

     List<ProductResponse> findAll();

     Long createProduct(ProductDTO productDTO);

     void deleteProduct(Long id);

     void updateProduct(ProductDTO productDTO, Long id);
}
