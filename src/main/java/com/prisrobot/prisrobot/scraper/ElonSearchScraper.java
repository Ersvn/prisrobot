package com.prisrobot.prisrobot.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ElonSearchScraper {

    public List<ScrapedProduct> search(String url) {
        List<ScrapedProduct> results = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            Elements items = doc.select(".product-item");

            for (Element item : items) {
                Element link = item.selectFirst(".product-item-link");
                Element priceEl = item.selectFirst(".price__value");

                if (link == null || priceEl == null) continue;

                String name = link.text();
                String productUrl = link.attr("href");

                String priceText = priceEl.text()
                        .replaceAll("[^0-9,\\.]", "")
                        .replace(",", ".");

                double price = Double.parseDouble(priceText);

                results.add(new ScrapedProduct(name, productUrl, price));
            }

        } catch (Exception e) {
            System.out.println("Elon search scraping error: " + e.getMessage());
        }

        return results;
    }
}
