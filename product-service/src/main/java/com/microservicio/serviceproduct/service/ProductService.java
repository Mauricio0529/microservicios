package com.microservicio.serviceproduct.service;

import com.microservicio.serviceproduct.models.Category;
import com.microservicio.serviceproduct.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public Product getProduct(Long id);

    public List<Product> getAll();

    public List<Product> getByCategory(Category category);

    public Product save(Product product);

    public Optional<Product> update(Product product);

    public Optional<Product> updateStock(Long id, Double stock);

    public boolean delete(Long id);
}