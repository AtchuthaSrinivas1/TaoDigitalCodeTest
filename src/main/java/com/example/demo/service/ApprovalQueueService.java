package com.example.demo.service;

import com.example.demo.domain.resonse.ApprovalQueueResponseDTO;
import com.example.demo.entity.ApprovalQueue;
import com.example.demo.entity.Product;
import com.example.demo.exception.ApprovalQueueNotFoundException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.mapper.ApprovalQueueMapper;
import com.example.demo.repository.ApprovalQueueRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApprovalQueueService {
    private final ApprovalQueueRepository approvalQueueRepository;
    private final ApprovalQueueMapper approvalQueueMapper;
    private final ProductRepository productRepository;


    public ApprovalQueueService(ApprovalQueueRepository approvalQueueRepository,
                                ApprovalQueueMapper approvalQueueMapper,
                                ProductRepository productRepository) {
        this.approvalQueueRepository = approvalQueueRepository;
        this.approvalQueueMapper = approvalQueueMapper;
        this.productRepository = productRepository;
    }

    public void addToApprovalQueue(Product product) {
        ApprovalQueue approvalQueue = new ApprovalQueue();
        approvalQueue.setProductId(product.getId());
        approvalQueue.setProductName(product.getName());
        approvalQueue.setProductPrice(product.getPrice());
        approvalQueue.setProductPostedDate(product.getPostedDate());
        approvalQueue.setApproved(false);
        approvalQueueRepository.save(approvalQueue);
    }

    public List<ApprovalQueueResponseDTO> getProductsInApprovalQueue() {
        List<ApprovalQueue> approvalQueueList = approvalQueueRepository.findAllByOrderByRequestDateAsc();
        return approvalQueueList.stream()
                .map(approvalQueueMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void approveProduct(Long approvalId) {
        ApprovalQueue approvalQueue = approvalQueueRepository.findById(approvalId)
                .orElseThrow(() -> new ApprovalQueueNotFoundException("Approval queue entry not found with id: " + approvalId));

        Long productId = approvalQueue.getProductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
        product.setStatus(true);
        productRepository.save(product);

        // Delete the approval queue entry
        approvalQueueRepository.delete(approvalQueue);
    }

    public void rejectProduct(Long approvalId) {
        ApprovalQueue approvalQueue = approvalQueueRepository.findById(approvalId)
                .orElseThrow(() -> new ApprovalQueueNotFoundException("Approval queue entry not found with id: " + approvalId));

        // Delete the approval queue entry
        approvalQueueRepository.delete(approvalQueue);
    }
}
