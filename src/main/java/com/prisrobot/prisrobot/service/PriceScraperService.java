package com.prisrobot.prisrobot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class PriceScraperService {

    private static final Logger log = LoggerFactory.getLogger(PriceScraperService.class);
    //------------------------------------------------------//
    //---Slå AV scraping och använd dummy-priser istället---//
    //------------------------------------------------------//
    private final boolean useDummyPrices = true;

    public Optional<Integer> scrapePrice(String url) {

        log.info("Startar prisuppdatering för URL: {}", url);

        //-------------------------//
        // DUMMY-PRIS AKTIVERAT    //
        //-------------------------//
        if (useDummyPrices) {
            int dummyPrice = generateDummyPrice(url);
            log.warn("Dummy-pris används istället för scraping: {}", dummyPrice);
            return Optional.of(dummyPrice);
        }

        //---------------------------//
        // RIKTIG SCRAPING (avstängd)//
        //---------------------------//
        log.warn("Scraping är avstängt i denna version. Aktivera useDummyPrices = false för att testa scraping.");
        return Optional.empty();
    }
    //---------------------------------//
    //---DUMMY PRISER baserat på URL---//
    //---------------------------------//
    private int generateDummyPrice(String url) {
        int base = Math.abs(url.hashCode() % 3000) + 1000; // 1000–3999 kr
        return base;
    }
}