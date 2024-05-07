package com.example.demo.configuration;

import com.example.demo.mapper.ApprovalQueueMapper;
import com.example.demo.mapper.ProductMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ProductMapper productMapper() {
        return Mappers.getMapper(ProductMapper.class);
    }

    @Bean
    public ApprovalQueueMapper approvalQueueMapper() {
        return Mappers.getMapper(ApprovalQueueMapper.class);
    }
}

