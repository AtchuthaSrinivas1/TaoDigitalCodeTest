package com.example.demo.controller;

import com.example.demo.domain.resonse.ApprovalQueueResponseDTO;
import com.example.demo.domain.resonse.ProductResponseDTO;
import com.example.demo.service.ApprovalQueueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products/approval-queue")
public class ApprovalQueueController {
    private final ApprovalQueueService approvalQueueService;

    public ApprovalQueueController(ApprovalQueueService approvalQueueService) {
        this.approvalQueueService = approvalQueueService;
    }

    @GetMapping
    public ResponseEntity<List<ApprovalQueueResponseDTO>> getProductsInApprovalQueue() {
        List<ApprovalQueueResponseDTO> products = approvalQueueService.getProductsInApprovalQueue();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/{approvalId}/approve")
    public ResponseEntity<?> approveProduct(@PathVariable Long approvalId) {
            approvalQueueService.approveProduct(approvalId);
            return new ResponseEntity<>("Product approved successfully", HttpStatus.OK);
    }

    @PutMapping("/{approvalId}/reject")
    public ResponseEntity<?> rejectProduct(@PathVariable Long approvalId) {
            approvalQueueService.rejectProduct(approvalId);
            return new ResponseEntity<>("Product rejected successfully", HttpStatus.OK);
    }
}
