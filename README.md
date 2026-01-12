## 📘 README – Prisrobot (Price Scraper App)
## 📝 Översikt
Prisrobot är en intern applikation utvecklad för att samla in, lagra och jämföra produktpriser.
Systemet består av:
- Backend (Spring Boot) – hanterar produkter, prisuppdateringar och scraping
- Frontend (React) – visar produkter, priser och låter användaren uppdatera dem
- Databas (JPA/Hibernate) – lagrar produkter och deras senaste pris
- Scraper‑service – hämtar pris från en URL (just nu med dummy‑priser)
Applikationen är byggd för intern användning och är inte avsedd att vara publik.

## 🧩 Funktioner
- Lägg till, uppdatera och ta bort produkter
- Visa produktlista med aktuella priser
- Uppdatera pris manuellt via frontend
- Dummy‑priser för utveckling (stabila och baserade på URL)
- Förberedd för framtida:
- scheduler (automatiska prisuppdateringar)
- riktig scraping
- API‑integrationer
- 
## 🏗 Arkitektur
Frontend (React)
##      ↓
Backend API (Spring Boot)
##      ↓
PriceScraperService (dummy eller scraping)
##      ↓
Database (JPA/Hibernate)

## 🔧 Scraping / Dummy‑priser
I nuläget används dummy‑priser eftersom de flesta butiker laddar priser via JavaScript eller skyddas av Cloudflare, vilket gör traditionell HTML‑scraping opålitlig.
Dummy‑priserna är:
- stabila per produkt
- baserade på URL:ens hash
- mellan 1000–3999 kr
- perfekta för utveckling och testning

## 🚀 Köra projektet
Backend
mvn spring-boot:run

Frontend
npm install
npm start

## 🗄 Databas
Projektet använder JPA/Hibernate och skapar tabeller automatiskt.
Standardentiteten för produkter innehåller:
- id
- code
- name
- url
- ownPrice
- externalPrice
- type
- ean

## 🔮 Planerade funktioner
- Automatisk prisuppdatering via scheduler
- Pris‑historik och grafer
- Notiser vid prisfall
- Integration med butikernas egna API:er
- Möjlighet att ersätta dummy‑priser med:
- headless scraping
- interna butik‑API:er
- Pricerunner‑API (vid avtal)

## ⚠️ Juridisk notis
Applikationen är avsedd för intern användning.
Scraping av externa webbplatser kan vara begränsat av:
- användarvillkor
- robots.txt
- tekniska skydd
Dummy‑priser används för att undvika juridiska och tekniska problem under utveckling.


