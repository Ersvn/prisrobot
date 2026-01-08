package com.prisrobot.prisrobot.scraper;

public class ScrapedProduct {
    public String name;
    public String url;
    public double price;

    public ScrapedProduct(String name, String url, double price) {
        this.name = name;
        this.url = url;
        this.price = price;
    }
}

