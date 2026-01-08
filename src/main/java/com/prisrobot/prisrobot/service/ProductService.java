package com.prisrobot.prisrobot.service;

import com.prisrobot.prisrobot.scraper.PriceScraper;
import com.prisrobot.prisrobot.model.Product;
import com.prisrobot.prisrobot.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final PriceScraper scraper;
    public ProductService(ProductRepository repo, PriceScraper scraper) {
        this.repo = repo;
        this.scraper = scraper;
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Product create(Product product) {
        return repo.save(product);
    }

    public Product updatePriceFromScraper(Long id) {
        Product p = repo.findById(id).orElseThrow();
        double scrapedPrice = scraper.fetchPrice(p.getUrl());
        p.setPrice(scrapedPrice);
        return repo.save(p);
    }

    public void deleteProduct(Long id) {
        repo.deleteById(id);
    }


}

