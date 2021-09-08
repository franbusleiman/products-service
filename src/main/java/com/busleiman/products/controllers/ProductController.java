package com.busleiman.products.controllers;

import com.busleiman.products.domain.dtos.ProductDTO;
import com.busleiman.products.domain.dtos.responses.ProductResponse;
import com.busleiman.products.domain.entities.Product;
import com.busleiman.products.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long id) throws InterruptedException {

        logger.info("Looking for a product");

        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponse>> getProducts(){

        return ResponseEntity.ok(productService.findAll());
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createProduct(@RequestBody ProductDTO productDTO){

        Long productId = productService.createProduct(productDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productId)
                .toUri();

        return ResponseEntity.created(location).build();
    }
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id,
                                                 @RequestBody ProductDTO productDTO){

        productService.updateProduct(productDTO, id);

        return ResponseEntity.status(204).build();
    }
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id){

        productService.deleteProduct(id);

        return ResponseEntity.status(204).build();
    }
}
