package com.prisrobot.prisrobot.controller;

import com.prisrobot.prisrobot.model.Product;
import com.prisrobot.prisrobot.service.ProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.prisrobot.prisrobot.scraper.ScrapedProduct;
import com.prisrobot.prisrobot.scraper.ElonSearchScraper;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ElonSearchScraper elonSearchScraper;
    private final ProductService service;

    public ProductController(ProductService service, ElonSearchScraper elonSearchScraper) {
        this.service = service;
        this.elonSearchScraper = elonSearchScraper;
    }

    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }

    @PutMapping("/{id}/update-price")
    public Product updatePrice(@PathVariable Long id) {
        return service.updatePriceFromScraper(id);
    }

    @GetMapping("/search")
    public List<ScrapedProduct> search(@RequestParam String url) {
        return elonSearchScraper.search(url);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
    }
}