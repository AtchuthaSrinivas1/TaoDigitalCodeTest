package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusOrderByPostedDateDesc(boolean status);

    @Query("SELECT p FROM Product p WHERE " +
            "(:productName IS NULL OR p.name LIKE %:productName%) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:minPostedDate IS NULL OR p.postedDate >= PARSEDATETIME(:minPostedDate, 'yyyy-MM-dd')) " +
            "AND (:maxPostedDate IS NULL OR p.postedDate <= PARSEDATETIME(:maxPostedDate, 'yyyy-MM-dd'))")
    List<Product> findByCriteria(
            @Param("productName") String productName,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minPostedDate") String minPostedDate,
            @Param("maxPostedDate") String maxPostedDate);

}
