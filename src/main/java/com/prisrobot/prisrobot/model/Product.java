package com.prisrobot.prisrobot.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // används endast internt av JPA

    @Column(unique = true, nullable = false)
    private String code; // din primära identifierare i API:et

    private String name;
    private String ean;
    private String type;

    private BigDecimal ownPrice;
    private BigDecimal externalPrice;

    private String url;

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getOwnPrice() {
        return ownPrice;
    }

    public void setOwnPrice(BigDecimal ownPrice) {
        this.ownPrice = ownPrice;
    }

    public BigDecimal getExternalPrice() {
        return externalPrice;
    }

    public void setExternalPrice(BigDecimal externalPrice) {
        this.externalPrice = externalPrice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

