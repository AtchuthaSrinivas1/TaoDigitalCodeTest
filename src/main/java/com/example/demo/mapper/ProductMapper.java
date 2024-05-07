package com.example.demo.mapper;

import com.example.demo.domain.request.ProductRequestDTO;
import com.example.demo.domain.resonse.ProductResponseDTO;
import com.example.demo.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {
    @Mapping(target = "postedDate", ignore = true)
    Product toEntity(ProductRequestDTO productRequestDTO);

    ProductResponseDTO toDTO(Product product);

    default boolean mapStatus(boolean status) {
        return status;
    }
}
