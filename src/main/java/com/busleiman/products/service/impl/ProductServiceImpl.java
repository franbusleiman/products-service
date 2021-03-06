package com.busleiman.products.service.impl;

import com.busleiman.products.domain.comparators.ProductNameComparator;
import com.busleiman.products.domain.comparators.ProductPriceComparator;
import com.busleiman.products.domain.dtos.ProductDTO;
import com.busleiman.products.domain.dtos.responses.ProductResponse;
import com.busleiman.products.domain.entities.Factory;
import com.busleiman.products.domain.entities.Product;
import com.busleiman.products.domain.entities.Section;
import com.busleiman.products.domain.mappers.ProductMapper;
import com.busleiman.products.exceptions.NotFoundException;
import com.busleiman.products.persistance.FactoryRepository;
import com.busleiman.products.persistance.ProductRepository;
import com.busleiman.products.persistance.SectionRepository;
import com.busleiman.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FactoryRepository factoryRepository;

    @Autowired
    private SectionRepository sectionRepository;

    private ProductNameComparator nameComparator = ProductNameComparator.INSTANCE;

    private ProductPriceComparator priceComparator = ProductPriceComparator.INSTANCE;

    private ProductMapper productMapper = ProductMapper.INSTANCE;


    @Override
    public ProductResponse findById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Resource not found"));

        return productMapper.productToProductResponse(product);
    }

    @Override
    public List<ProductResponse> findAll() {

        return productRepository.findAll()
                .stream()
                .map(product -> productMapper.productToProductResponse(product))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllOrderedByName() {
        return productRepository.findAll()
                .stream()
                .sorted(nameComparator)
                .map(product -> productMapper.productToProductResponse(product))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllOrderedByPrice() {
        return productRepository.findAll()
                .stream()
                .sorted(priceComparator)
                .map(product -> productMapper.productToProductResponse(product))
                .collect(Collectors.toList());
    }

    @Override
    public Long create(ProductDTO productDTO) {

        Factory factory = factoryRepository.findById(productDTO.getFactoryId())
                .orElseThrow(() -> new NotFoundException("Factory not found with id: " + productDTO.getFactoryId()));

        Section section = sectionRepository.findById(productDTO.getSectionId())
                .orElseThrow(() -> new NotFoundException("Section not found with id: " + productDTO.getSectionId()));

        Product product = productMapper.ProductDTOtoProduct(productDTO);

        product.setCreateAt(LocalDate.now());

        if (factory.getProductList() == null) factory.setProductList(new ArrayList<>());


        factory.getProductList().add(product);
        product.setFactory(factory);

        if (section.getProductList() == null) section.setProductList(new ArrayList<>());

        section.getProductList().add(product);
        product.setSection(section);

        return productRepository.save(product).getId();
    }

    @Override
    public void delete(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        productRepository.delete(product);
    }

    @Override
    public void update(ProductDTO productDTO, Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if (productDTO.getSectionId() != null) {
            Section section = sectionRepository.findById(productDTO.getSectionId())
                    .orElseThrow(() -> new NotFoundException("Section not found with id: " + productDTO.getSectionId()));

            if (product.getSection().getId() != section.getId()) {

                Section section1 = product.getSection();

                section1.getProductList().remove(product);

                sectionRepository.save(section1);

                if (section.getProductList() == null) section.setProductList(new ArrayList<>());
                section.getProductList().add(product);
                product.setSection(section);
            }
        }
        if (productDTO.getPrice() != null) {
            product.setPrice(productDTO.getPrice());
        }
        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }

        productRepository.save(product);
    }
}
