package com.microservicio.serviceproduct.service;

import com.microservicio.serviceproduct.models.Category;
import com.microservicio.serviceproduct.models.Product;
import com.microservicio.serviceproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product save(Product product) {
        product.setStatus("ACTIVO");
        product.setCreateAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> update(Product product) {
        Product getProduct = getProduct(product.getId());
        if(getProduct != null) {
            productRepository.save(product);
            return Optional.of(getProduct);
        }
        return Optional.empty();
    }

    /**
     * Agregar cantidad al stock
     * @param id Id de producto
     * @param newQuantity Nueva cantidad para sumar al stock
     * @return Optional de prouducto con su stock actualizado
     */
    @Override
    public Optional<Product> updateStock(Long id, Double newQuantity) {
        Product product = getProduct(id);

        if(product != null) {
            Double newStock = product.getStock() + newQuantity;

            product.setStock(newStock);

            productRepository.save(product);
            return Optional.of(product);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        Product product = getProduct(id);
        if(product != null) {
            product.setStatus("DELETED");
            productRepository.save(product);
            return true;
        }
        return false;
    }
}