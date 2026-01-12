package com.prisrobot.prisrobot.service;

import com.prisrobot.prisrobot.model.Product;
import com.prisrobot.prisrobot.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final PriceScraperService priceScraperService;

    public ProductService(ProductRepository repo, PriceScraperService priceScraperService) {
        this.repo = repo;
        this.priceScraperService = priceScraperService;
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Optional<Product> getByCode(String code) {
        return repo.findByCode(code);
    }

    public Product save(Product product) {
        return repo.save(product);
    }

    @Transactional
    public void deleteByCode(String code) {
        repo.deleteByCode(code);
    }

    public Optional<Product> updateExternalPrice(String code) {
        return repo.findByCode(code).map(product -> {

            Optional<Integer> scrapedPriceOpt = priceScraperService.scrapePrice(product.getUrl());

            scrapedPriceOpt.ifPresent(priceInt -> {
                BigDecimal price = BigDecimal.valueOf(priceInt);
                product.setExternalPrice(price);
                repo.save(product);
            });

            return product;
        });
    }
}
