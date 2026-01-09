package com.prisrobot.prisrobot.service;

import com.prisrobot.prisrobot.model.Product;
import com.prisrobot.prisrobot.repository.ProductRepository;
import com.prisrobot.prisrobot.scraper.PriceScraper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final PriceScraper priceScraper;

    public ProductService(ProductRepository repo, PriceScraper priceScraper) {
        this.repo = repo;
        this.priceScraper = priceScraper;
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

    public void deleteByCode(String code) {
        repo.deleteByCode(code);
    }

    public Optional<Product> updateExternalPrice(String code) {
        return repo.findByCode(code).map(product -> {
            BigDecimal scrapedPrice = priceScraper.scrapePrice(product.getUrl());
            if (scrapedPrice != null) {
                product.setExternalPrice(scrapedPrice);
                repo.save(product);
            }
            return product;
        });
    }
}

