package com.busleiman.products.service;

import com.busleiman.products.domain.entities.Product;

import java.util.List;

public interface ProductService {

     Product findById(Long id);

     List<Product> findAll();

     Product createProduct(Product product);

     void deleteProduct(Long id);

     Product updateProduct(Product product, Long id);
}
