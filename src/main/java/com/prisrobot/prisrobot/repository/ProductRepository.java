package com.prisrobot.prisrobot.repository;

import com.prisrobot.prisrobot.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByCode(String code);
@Transactional
    void deleteByCode(String code);
}