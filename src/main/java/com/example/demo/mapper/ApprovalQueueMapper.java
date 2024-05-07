package com.example.demo.mapper;

import com.example.demo.domain.request.ApprovalQueueRequestDTO;
import com.example.demo.domain.resonse.ApprovalQueueResponseDTO;
import com.example.demo.entity.ApprovalQueue;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ApprovalQueueMapper {
    default ApprovalQueueResponseDTO toResponseDTO(ApprovalQueue approvalQueue) {
        ApprovalQueueResponseDTO dto = new ApprovalQueueResponseDTO();
        dto.setId(approvalQueue.getId());
        dto.setProductId(approvalQueue.getProductId());
        dto.setRequestDate(approvalQueue.getRequestDate());
        dto.setApproved(approvalQueue.isApproved());
        dto.setProductName(approvalQueue.getProductName());
        dto.setProductPrice(approvalQueue.getProductPrice());
        dto.setProductPostedDate(approvalQueue.getProductPostedDate());
        return dto;
    }

    List<ApprovalQueueResponseDTO> toResponseDTOList(List<ApprovalQueue> approvalQueueList);

    ApprovalQueue toEntity(ApprovalQueueRequestDTO approvalQueueRequestDTO);
}
