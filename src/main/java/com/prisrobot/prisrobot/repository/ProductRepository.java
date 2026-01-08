package com.prisrobot.prisrobot.repository;

import com.prisrobot.prisrobot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
