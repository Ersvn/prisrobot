package com.prisrobot.prisrobot.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;

@Component
public class PriceScraper {

    public BigDecimal scrapePrice(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10_000)
                    .get();

            Elements priceEl = doc.select(".price");

            if (!priceEl.isEmpty()) {
                String raw = priceEl.first().text();
                return parsePrice(raw);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private BigDecimal parsePrice(String raw) {
        if (raw == null) return null;

        String cleaned = raw
                .replace(":-", "")
                .replace("kr", "")
                .replace("&nbsp;", "")
                .replace("\u00A0", "")
                .replace(" ", "")
                .replace(",", ".")
                .trim();

        try {
            return new BigDecimal(cleaned);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}


