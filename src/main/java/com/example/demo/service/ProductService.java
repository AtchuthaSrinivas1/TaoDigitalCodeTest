package com.example.demo.service;

import com.example.demo.domain.request.ProductRequestDTO;
import com.example.demo.entity.Product;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.exception.ProductPriceExceededException;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ApprovalQueueService approvalQueueService;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper,
                          ApprovalQueueService approvalQueueService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.approvalQueueService = approvalQueueService;
    }

    public List<Product> getActiveProducts() {
        return productRepository.findByStatusOrderByPostedDateDesc(true);
    }

    public List<Product> searchProducts(String productName, BigDecimal minPrice, BigDecimal maxPrice,
                                        LocalDate minPostedDate, LocalDate maxPostedDate) {
        String minDate = minPostedDate != null ? minPostedDate.format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
        String maxDate = maxPostedDate != null ? maxPostedDate.format(DateTimeFormatter.ISO_LOCAL_DATE) : null;

        return productRepository.findByCriteria(productName, minPrice, maxPrice,
                minDate, maxDate);
    }

    @Transactional
    public void createProduct(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toEntity(productRequestDTO);
        if (product.getPrice().compareTo(BigDecimal.valueOf(10000)) > 0) {
            throw new ProductPriceExceededException("Price cannot exceed $10,000");
        } else if (product.getPrice().compareTo(BigDecimal.valueOf(5000)) > 0) {
            product.setStatus(false);
            product = productRepository.save(product);
            approvalQueueService.addToApprovalQueue(product);
        } else {
            product.setStatus(true);
            productRepository.save(product);
        }
    }

    @Transactional
    public Product updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        Product updatedProduct = productMapper.toEntity(productRequestDTO);

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        BigDecimal previousPrice = existingProduct.getPrice();
        BigDecimal newPrice = updatedProduct.getPrice();

        if (newPrice.compareTo(previousPrice.multiply(BigDecimal.valueOf(1.5))) > 0) {
            // Price increase exceeds 50%, push to approval queue
            updatedProduct.setStatus(false);
        } else {
            updatedProduct.setStatus(true);
        }
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStatus(updatedProduct.isStatus());
        existingProduct = productRepository.save(existingProduct);

        // Add to approval queue
        if (!existingProduct.isStatus()) {
            approvalQueueService.addToApprovalQueue(existingProduct);
        }

        return existingProduct;
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        // Push the product to the approval queue
        approvalQueueService.addToApprovalQueue(product);

        // Delete the product
        productRepository.delete(product);
    }
}
