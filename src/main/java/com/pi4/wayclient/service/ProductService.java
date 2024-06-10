package com.pi4.wayclient.service;

import com.pi4.wayclient.model.Employee;
import com.pi4.wayclient.model.Product;
import com.pi4.wayclient.repository.EmployeeRepository;
import com.pi4.wayclient.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Optional<Product> getProduct(UUID id) {
        return productRepository.findById(id);
    }

    //TODO: Refatorar: alterar função p/ seguir padrão de update dos outros services
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
