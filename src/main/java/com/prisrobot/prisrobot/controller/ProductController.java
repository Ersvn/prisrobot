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

    //---GET ALL---//
    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    //---GET BY CODE---//
    @GetMapping("/{code}")
    public ResponseEntity<Product> getByCode(@PathVariable String code) {
        return service.getByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //---CREATE PRODUCT---//
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product saved = service.save(product);
        return ResponseEntity.ok(saved);
    }

    //---UPDATE PRODUCT---//
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
                    existing.setCode(updated.getCode()); // valfritt om du vill tillåta ändring av code

                    Product saved = service.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //---UPDATE PRICE (SCRAPING / API)---//
    @PutMapping("/{code}/update-price")
    public ResponseEntity<Product> updatePrice(@PathVariable String code) {
        return service.updateExternalPrice(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //---DELETE PRODUCT---//
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        if (service.getByCode(code).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }

    //---EXPORT CSV---//
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");

        String today = java.time.LocalDate.now().toString();
        response.setHeader("Content-Disposition", "attachment; filename=products_" + today + ".csv");

        List<Product> products = service.getAll();

        PrintWriter writer = response.getWriter();
        writer.println("Code;Namn;EAN;Typ;Eget pris;Externt pris;Diff;Diff %;URL");

        for (Product p : products) {

            double own = p.getOwnPrice() != null ? p.getOwnPrice().doubleValue() : 0;
            double ext = p.getExternalPrice() != null ? p.getExternalPrice().doubleValue() : 0;

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