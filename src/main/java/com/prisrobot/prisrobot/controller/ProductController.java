package com.prisrobot.prisrobot.controller;

import com.prisrobot.prisrobot.model.Product;
import com.prisrobot.prisrobot.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Product> getByCode(@PathVariable String code) {
        return service.getByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.save(product);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Product> update(@PathVariable String code, @RequestBody Product updated) {
        return service.getByCode(code)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setEan(updated.getEan());
                    existing.setType(updated.getType());
                    existing.setOwnPrice(updated.getOwnPrice());
                    existing.setExternalPrice(updated.getExternalPrice());
                    existing.setUrl(updated.getUrl());
                    existing.setCode(updated.getCode()); // om du vill kunna ändra code
                    return ResponseEntity.ok(service.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{code}/update-price")
    public ResponseEntity<Product> updatePrice(@PathVariable String code) {
        return service.updateExternalPrice(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        if (service.getByCode(code).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");

        String today = java.time.LocalDate.now().toString();
        response.setHeader("Content-Disposition", "attachment; filename=products_" + today + ".csv");

        List<Product> products = service.getAll();

        PrintWriter writer = response.getWriter();
        writer.println("Code;Namn;EAN;Typ;Eget pris;Externt pris;Diff;Diff %;URL");

        for (Product p : products) {

            double own = p.getOwnPrice().doubleValue();
            double ext = p.getExternalPrice().doubleValue();
            double diff = own - ext;
            double percent = ext > 0 ? (diff / ext) * 100 : 0;

            writer.println(
                    safe(p.getCode()) + ";" +
                            safe(p.getName()) + ";" +
                            safe(p.getEan()) + ";" +
                            safe(p.getType()) + ";" +
                            safe(p.getOwnPrice()) + ";" +
                            safe(p.getExternalPrice()) + ";" +
                            diff + ";" +
                            String.format("%.1f%%", percent) + ";" +
                            "=HYPERLINK(\"" + safe(p.getUrl()) + "\";\"Länk\")"
            );
        }
    }

    private String safe(Object o) {
        return o == null ? "" : o.toString().replace(";", " ");
    }
}