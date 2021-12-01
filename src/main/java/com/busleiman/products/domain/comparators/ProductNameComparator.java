package com.busleiman.products.domain.comparators;

import com.busleiman.products.domain.entities.Product;

import java.util.Comparator;

public class ProductNameComparator implements Comparator<Product> {

    public static ProductNameComparator INSTANCE = new ProductNameComparator();

    @Override
    public int compare(Product o1, Product o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
