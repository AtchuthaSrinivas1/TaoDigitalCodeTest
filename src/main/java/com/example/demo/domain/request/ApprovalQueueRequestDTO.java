package com.example.demo.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApprovalQueueRequestDTO {
    private Long productId;
    private LocalDateTime requestDate;
    private boolean approved;
}
