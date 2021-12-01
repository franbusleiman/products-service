package com.busleiman.products.domain.comparators;

import com.busleiman.products.domain.entities.Product;

import java.util.Comparator;

public class ProductPriceComparator implements Comparator<Product > {

    public static ProductPriceComparator INSTANCE = new ProductPriceComparator();

    @Override
    public int compare(Product o1, Product o2) {
        return Double.compare(o1.getPrice(), o2.getPrice());
    }
}
