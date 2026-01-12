package com.prisrobot.prisrobot.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PriceScraperService {

    public Optional<Integer> scrapePrice(String url) {
        // TODO: Byt ut detta mot riktig scraping eller API-anrop senare

        // Exempel: returnera ett fejk-pris bara för att testa flödet
        // Du kan t.ex. slumpa ett pris eller basera på något i URL:en
        // Här returnerar vi ett fast pris för enkelhetens skull
        int fakePrice = 1999;

        return Optional.of(fakePrice);
    }
}
