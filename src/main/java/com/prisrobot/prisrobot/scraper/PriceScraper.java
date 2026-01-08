package com.prisrobot.prisrobot.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class PriceScraper {

    public double fetchPrice(String url) {
        try {

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            // Detta måste anpassas per butik senare
            Element priceElement = doc.selectFirst(".price, .product-price, .Price, .price-tag");

            if (priceElement == null) {
                System.out.println("Kunde inte hitta pris på sidan.");
                return -1;
            }

            String priceText = priceElement.text()
                    .replaceAll("[^0-9,\\.]", "")
                    .replace(",", ".");

            return Double.parseDouble(priceText);

        } catch (Exception e) {
            System.out.println("Scraping error: " + e.getMessage());
            return -1;
        }
    }
}


