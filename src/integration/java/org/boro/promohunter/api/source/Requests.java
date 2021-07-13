package org.boro.promohunter.api.source;

public final class Requests {

    public final static String BOTLAND_SOURCE_REQUEST = """
        {
            "name": "botland.com.pl",
            "description": "Internetowy sklep elektroniczny Botland - Sklep dla robotyków",
            "url": "https://botland.com.pl/",
            "nameSelector": "#main h1",
            "descriptionSelector": ".product-page__short-description.mb-4.mt-2",
            "priceSelector": "#add-to-cart-or-refresh .current-price-display"
        }
        """;

    public final static String MORELE_SOURCE_REQUEST = """
        {
            "name": "www.morele.net",
            "description": "Morele - zakupy online to pestka",
            "url": "https://www.morele.net",
            "nameSelector": "#product h1.prod-name",
            "descriptionSelector": "#product div.panel-description",
            "priceSelector": "#product_price_brutto"
        }
        """;

    public final static String XKOM_SOURCE_REQUEST = """
        {
            "name": "www.x-kom.pl",
            "description": "Odbiór za 0 zł w dowolnym salonie, błyskawiczna wysyłka. Zapewniamy inteligentny wybór.",
            "url": "https://botland.com.pl/",
            "nameSelector": "#app h1",
            "descriptionSelector": "#app .product-description.content.product-page",
            "priceSelector": "#app div.u7xnnm-0.qQcR div"
        }
        """;

    /**
     * Original url: https://www.morele.net/karta-graficzna-gigabyte-geforce-rtx-3080-gaming-oc-10gb-gddr6x-gv-n3080gaming-oc-10gd-5943775/
     */
    public final static String CHECK_MORELE_SOURCE_REQUEST = """
        {
            "source": {
                "name": "https://www.morele.net",
                "description": "Morele - zakupy online to pestka",
                "url": "https://www.morele.net",
                "nameSelector": "#product h1.prod-name",
                "descriptionSelector": "#product div.panel-description",
                "priceSelector": "#product_price_brutto"
            },
            "url": "http://localhost:9091/karta-graficzna-morele.html"
        }
        """;

    /**
     * Original url: https://botland.com.pl/pl/moduly-i-zestawy-raspberry-pi-4b/14751-zestaw-z-raspberry-pi-4b-wifi-4gb-ram-32gb-microsd-oficjalne-akcesoria-5903351242554.html
     */
    public final static String CHECK_BOTLAND_SOURCE_REQUEST = """
        {
            "source": {
                "name": "botland.com.pl",
                "description": "Internetowy sklep elektroniczny Botland - Sklep dla robotyków",
                "url": "https://botland.com.pl/",
                "nameSelector": "#main h1",
                "descriptionSelector": ".product-page__short-description.mb-4.mt-2",
                "priceSelector": "#add-to-cart-or-refresh .current-price-display"
            },
            "url": "http://localhost:9091/raspberry-pi-botland.html"
        }
        """;

    /**
     * Original url: https://www.x-kom.pl/p/597344-karta-graficzna-nvidia-gigabyte-geforce-rtx-3070-eagle-8gb-gddr6.html?utm_medium=zestawy-komputerowe-202105&utm_source=techlipton&utm_campaign=lipton-zestaw-za-6000
     */
    public final static String CHECK_XKOM_SOURCE_REQUEST = """
        {
            "source": {
                "name": "www.x-kom.pl",
                "description": "Odbiór za 0 zł w dowolnym salonie, błyskawiczna wysyłka. Zapewniamy inteligentny wybór.",
                "url": "https://botland.com.pl/",
                "nameSelector": "#app h1",
                "descriptionSelector": "#app .product-description.content.product-page",
                "priceSelector": "#app div.u7xnnm-0.qQcR div"
            },
            "url": "http://localhost:9091/karta-graficzna-xkom.html"
        }
        """;

    public final static String NEW_SOURCE_REQUEST = BOTLAND_SOURCE_REQUEST;
    public final static String CHECK_SOURCE_REQUEST = CHECK_MORELE_SOURCE_REQUEST;

    private Requests() {
    }
}
