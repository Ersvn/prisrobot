package com.prisrobot.prisrobot.scraper;

import org.springframework.stereotype.Component;

@Component
public class PriceScraper {

    public double fetchPrice(String url) {
        System.out.println("Pretending to scrape: " + url);
        return -1; // placeholder
    }
}

