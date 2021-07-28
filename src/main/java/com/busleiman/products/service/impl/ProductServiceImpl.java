package com.busleiman.products.service.impl;

import com.busleiman.products.domain.Product;
import com.busleiman.products.persistance.ProductRepository;
import com.busleiman.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Product findById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {

         Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));

        productRepository.delete(product);

    }

    @Override
    public Product updateProduct(Product product, Long id) {

        Product product1 = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));

        product1.setPrice(product.getPrice());
        product1.setName(product.getName());
        product1.setCreateAt(product.getCreateAt());

        productRepository.save(product1);

        return product1;
    }
}
