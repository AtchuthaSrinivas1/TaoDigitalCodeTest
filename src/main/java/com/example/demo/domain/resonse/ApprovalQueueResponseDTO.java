package com.example.demo.domain.resonse;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ApprovalQueueResponseDTO {
    private Long id;
    private Long productId;
    private LocalDate requestDate;
    private boolean approved;
    private String productName;
    private BigDecimal productPrice;
    private LocalDate productPostedDate;

    public LocalDate getRequestDate() {
        return this.requestDate;
    }
}
