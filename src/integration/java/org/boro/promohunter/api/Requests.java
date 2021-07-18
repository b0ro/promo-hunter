package org.boro.promohunter.api;

public final class Requests {

    /**
     * Original url: https://botland.com.pl/
     */
    public final static String BOTLAND_SOURCE_REQUEST = """
        {
            "name": "botland.com.pl",
            "description": "Internetowy sklep elektroniczny Botland - Sklep dla robotyków",
            "url": "http://localhost:9091/",
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

    public final static String BOTLAND_ITEM_REQUEST = """
            {
                "name": "Zestaw z Raspberry Pi 4B WiFi 4GB RAM + 32GB microSD + oficjalne akcesoria",
                "description": "Zestaw zawiera minikomputer Raspberry Pi w najnowszej 4B z 4GB pamięci RAM z oficjalnym zasilaczem USB C 5,1 V / 3,0 A i oficjalną obudową W komplecie znajdują się również przewód microHDMI oraz karta pamięci 32 GB klasy 10 oraz przewodem Ethernet.",
                "url": "http://localhost:9091/raspberry-pi-botland.html",
                "price": 388.00
            }
            """;

    public final static String BOTLAND_ITEM2_REQUEST = """
            {
                "name": "Obudowa do Raspberry Pi 4B oficjalna - czerwono-biała",
                "description": "Oficjalna obudowa do minikomputera Raspberry Pi w wersji 4B o wymiarach zewnętrznych 90 x 60 x 30 mm. W zestawie znajdują się także nóżki antypoślizgowe.",
                "url": "http://localhost:9091/raspberry-pi-obudowa-botland.html",
                "price": 22.90
            }
            """;

    public final static String BOTLAND_ITEM3_REQUEST = """
            {
                "name": "GrovePi Zero Basic Kit - zestaw dla Raspberry Pi Zero - dla początkujących",
                "description": "Zestaw składający się z modułów z serii Grove, umożliwiający tworzenie prostych projektów przez początkujących użytkowników Raspberry Pi Zero. Zestaw zawiera Grove Pi0 oraz 12 modułów i czujników.",
                "url": "http://localhost:9091/grove-pi-zero-basic-kit-botland.html",
                "price": 388.00
            }
            """;

    public final static String MORELE_ITEM_REQUEST = """
            {
                "name": "Karta graficzna Gigabyte GeForce RTX 3080 Gaming OC 10GB GDDR6X (GV-N3080GAMING OC-10GD)",
                "description": "Hiperrealistyczna grafika Karta graficzna GeForce RTX 3080 GAMING OC 10GB (premiera: wrzesień 2020) to lider wśród gamingowych sprzętów tego typu. Bazuje na przyspieszonym układzie graficznym, dysponuje pojemną i szybką pamięcią. Już pierwsze testy udowodniły, że mamy do czynienia z bardzo dobrej jakości produktem. Karta doskonale radzi sobie z technologią Ray Tracing, zbyt skomplikowaną dla większości konkurentów. Ray Tracing (ang. śledzenie promieni) to prawdziwa rewolucja w świecie gier komputerowych. To dzięki niej widzisz w grze hiperrealistyczne efekty świetlne. Zaawansowane algorytmy bez trudu rozpoznają źródła światła, a cienie i odbicia renderują się w czasie rzeczywistym. W efekcie animacje w bestsellerowych produkcjach mają jeszcze lepszą jakość! Jest tylko jedno „ale” – Ray Tracing mocno spowalnia działanie gier, ale nie wtedy, gdy korzystasz z karty GeForce RTX 3080. Tutaj wszystko działa płynnie i poprawnie. Dzieje się tak z dwóch powodów. Po pierwsze, dzięki zaawansowanym możliwościom serii RTX. Po drugie, z racji udostępnionej przez NVIDIA techniki DLSS. Ta ostatnia w imponujący sposób przyspiesza renderowanie. Nie tylko zwiększa częstotliwość generowania klatek, ale również zachowuje najwyższą jakość obrazu, nawet kiedy używasz gier zaawansowanych graficznie. Jakość 8K i efekty 3D Renderowanie w czasie rzeczywistym to jednak dopiero ułamek możliwości, jakie oferuje nowy, gamingowy GeForce RTX. Karta obsługuje grafikę we wszystkich rozdzielczościach, nawet 8K! Zapewnia kinową jakość obrazu. Nie dość, że na ekranie monitora zobaczysz każdy detal, to jeszcze możesz cieszyć się technologią VR Ready w pełnym wymiarze. Mimo że gra toczy się w rzeczywistości wirtualnej, Ty dzięki możliwościom 3D jesteś w samym centrum akcji! Możesz sterować wirtualną rzeczywistością poprzez zmianę położenia kamery i kąt widzenia, a także zatrzymać najbardziej emocjonujące fragmenty gry! Wystarczy, że skorzystasz z możliwości technologii Ansel i zrobisz screeny z dowolnej perspektywy. Dokładność co do milisekundy Szeroki kąt widzenia i kinowa jakość obrazu to jednak jeszcze za mało, by wygrać wirtualną rozgrywkę. Absolutną przewagę zapewni Ci dopiero rozwiązanie NVIDIA Reflex. To ono odpowiada za najniższe opóźnienia i najlepszą responsywność. Ma to znaczenie zwłaszcza w grach turniejowych. Z pomocą tego narzędzia szybciej namierzysz cel, zareagujesz i zwiększysz swoją precyzję. Optymalne chłodzenie karty graficznej Praca na pełnych obrotach zazwyczaj wiąże się z dużymi obciążeniami. W przypadku serii GeForce RTX producent osiągnął jednak kompromis pomiędzy niską emisją hałasu a wysoką wydajnością. Zastosował autorskie chłodzenie, które zapewnia optymalne temperatury i dyskretną pracę. Trzy wentylatory działają w pełnym zakresie dopiero wówczas, gdy obciążenie układu graficznego jest naprawdę wysokie. Z pewnością docenisz to rozwiązanie, zwłaszcza że karta prezentuje się doskonale nie tylko wewnątrz. Jej gamingowy design z podświetleniem LED zwraca uwagę każdego gracza. Praca na kilku monitorach i transmisje na żywo Bardzo dobra jakość obrazu i dyskretna praca sprawiają, że użytkownicy często chcą zobaczyć możliwości opisywanej karty graficznej na większym ekranie. Producent wziął to pod uwagę i wyposażył model w nowoczesne systemy portów – złącza HDMI i DisplayPort. Dzięki temu bez problemu możesz przesłać sygnał audio i wideo bez utraty jakości. Niezależnie od tego, czy podłączysz monitor, telewizor czy projektor, będziesz mógł wyświetlać obrazy na dużym ekranie w rozdzielczości nawet 8K! Mając tak zaawansowaną kartę graficzną, możesz tworzyć również transmisje na żywo. Wideo udostępniane odbiorcom w czasie rzeczywistym wymaga bardzo solidnego sprzętu – właśnie takiego jak układy GPU GeForce RTX z serii 30. Na pewno szybko docenisz potężne możliwości SI, które zwiększają jakość dźwięku i obrazu. Do dyspozycji będziesz mieć m.in. efekty, takie jak wirtualne tło, automatyczne dopasowanie obrazu z kamery internetowej czy usuwanie szumu mikrofonu. Opisywana karta to bez wątpienia produkt premium, przeznaczony dla najbardziej wymagających graczy. Korzystanie z niej zapewnia ultrawydajność i lepszą jakość gamingu. Dodatkowo producent w przypadku tego modelu umożliwia przedłużenie gwarancji do 4 lat.",
                "url": "http://localhost:9091/karta-graficzna-morele.html",
                "price": 6999.0
            }
            """;

    public final static String XKOM_ITEM_REQUEST = """
            {
                "name": "Gigabyte GeForce RTX 3070 EAGLE 8GB GDDR6",
                "description": "Możesz kupić maksymalnie jedną sztukę karty graficznej – niezależnie od serii i modelu. Wszystkie zamówienia na więcej sztuk będziemy automatycznie edytować. Nie możesz kupić tej karty, jeśli masz już zamówienie na inny model. Dostępność kart graficznych jest uzależniona od dostaw producenta. Zamówienia realizujemy chronologicznie. Gigabyte GeForce RTX 3070 EAGLE 8GB Zmaksymalizuj doświadczenia płynące z gier i codziennej pracy, wyposażając swój komputer w unikalną kartę Gigabyte GeForce RTX 3070 EAGLE. GPU w architekturze NVIDIA Ampere, wspomagane przez 8GB pamięci GDDR6 oraz szereg wyjątkowych technologii, takich jak Ray Tracing, pozwolą Ci cieszyć się płynną rozgrywką z grafiką o fotorealistycznej jakości. A wydajny układ chłodzenia WINDFORCE 3X zadba o stabilną pracę karty. Wydajność dzięki architekturze NVIDIA Ampere Karta graficzna Gigabyte GeForce RTX 3070 EAGLE oparta została na nowatorskiej, bogatej w nowe technologie architekturze NVIDIA Ampere. To druga generacja układów NVIDIA RTX, która gwarantuje olbrzymi wzrost mocy obliczeniowej. Architektura zapewnia nawet dwukrotnie wyższą przepustowość dzięki drugiej generacji rdzeniom RT oraz trzeciej generacji rdzeniom Tensor. Maksymalna wydajność chłodzenia W karcie graficznej Gigabyte GeForce RTX 3070 EAGLE umieszczono wydajny układ chłodzenia wykorzystujący trzy unikalne wentylatory o średnicy 80mm. Przeciwne kierunki obrotu oraz specjalny kształt łopatek pozwoliły uzyskać wysokie ciśnienie oraz zredukować turbulencje. A przy niskim obciążeniu karta przełączy się na ciche chłodzenie półpasywne. Specjalne ukształtowane cztery kompozytowe ciepłowody przylegają bezpośrednio do GPU, by zmaksymalizować odprowadzanie ciepła. Dodatkowo łączą się z masywną płytką stykową obejmującą również sekcje VRAM. Ciepłowody zatopiono w masywnym radiatorze, gdzie ciepło rozprasza strumień powietrza generowany przez wentylatory. Unikalny design z podświetleniem RGB Karta Gigabyte GeForce RTX 3070 EAGLE została zamknięta w minimalistycznej obudowie, która będzie się doskonale prezentować w każdej obudowie. Obudowa wzbogacona o podświetlane logo RGB Fusion 2.0 nada Twojej konfiguracji wyjątkowego wyglądu. Metalowy backplate, ukształtowany tak by wspomagać wentylację, dodatkowo wzmacnia kartę i uzupełnia jej wyjątkowy design. Technologie graficzne Ray Tracing i DLSS Opracowany przez firmę NVIDIA Ray Tracing pozwala symulować fizyczne zachowanie światła, umożliwiając renderowanie w czasie rzeczywistym grafiki o kinowej jakości, nawet w przypadku najbardziej wymagających pod względem graficznym gier. NVIDIA DLSS to przełomowa technika renderingu oparta na sztucznej inteligencji. Korzysta z dedykowanych procesorów SI rdzeni Tensor oraz potęgi sieci neuronowej z zakresu głębokiego uczenia, aby zwiększyć częstotliwość generowania klatek przy zachowaniu pięknych, ostrych obrazów w grach. Deep Learning Super Sampling zapewnia także dodatkową wydajność pozwalając zmaksymalizować ustawienia Ray Tracingu i zwiększyć rozdzielczości wyjściowe. Frames Win Games: zwycięstwo mierzone w milisekundach Rozwiązanie NVIDIA Reflex zapewnia absolutną przewagę podczas rozgrywki, opierając się na układach GPU GeForce RTX z serii 30 oraz monitorach NVIDIA G-SYNC. Dynamicznie redukuje opóźnienia systemowe łącząc optymalizacje układów GPU i gier oraz zapewniając przewagę czasu reakcji. Prędzej namierzaj cel, szybciej reaguj i zwiększ precyzję celowania dzięki rewolucyjnemu zestawowi technik pomiaru i optymalizacji opóźnień systemowych w grach turniejowych. Zyskaj potrzebną przewagę prowadząc rozgrywkę przy 144 FPS lub więcej, korzystając z najszybszych kart graficznych opartych na układach NVIDIA GeForce. Większe możliwości dla streamerów z NVIDIA Broadcast Aplikacja NVIDIA Broadcast przekształca Twoją gamingową przestrzeń w domowe studio. Wznieś swoje transmisje, rozmowy głosowe i nagrania wideo na wyższy poziom dzięki dźwiękowi i obrazowi ulepszonym przez SI. Zakosztuj błyskawicznej kontroli twórczej dostępnej na wyciągniecie ręki. Spersonalizuj obraz z kamery internetowej korzystając z opartych na SI efektów i dodając zindywidualizowane tła. Dynamicznie śledź swoje ruchy w czasie rzeczywistym dzięki opcji automatycznego dopasowania obrazu. Eliminuj niepożądany hałas tła za dotknięciem przycisku dzięki funkcji usuwania szumu wykorzystującej SI. Twórz z prędkością wyobraźni z NVIDIA Studio Wspierając ambitnych artystów jak i profesjonalistów, platforma NVIDIA Studio turbodoładowuje proces twórczy. Wiodące w branży układy GPU NVIDIA, w połączeniu z wyjątkową technologią dotyczącą sterowników, inspirująco podnoszą wydajność i potencjał aplikacji kreatywnych. Sterowniki NVIDIA Studio stoją za każdym układem GPU NVIDIA i każdym twórcą. Zespoły testerów i inżynierów wspólnie z twórcami aplikacji kreatywnych nieustannie optymalizują działanie sprzętu NVIDIA z Twoimi ulubionymi aplikacjami kreatywnymi. Edytuj, obrabiaj, projektuj, animuj, wizualizuj i transmituj z prędkością wyobraźni. Dokonaj najlepszego wyboru Oparta na nowoczesnej architekturze Ampere karta graficzna GeForce RTX 3070 jest gotowa, by wesprzeć Cię w różnego rodzaju projektach i gamingowych podbojach. Zanim jednak wyląduje w Twoim komputerze, sprawdź co opowie o niej Piotr „Lipton” Szymański.",
                "url": "http://localhost:9091/karta-graficzna-xkom.html",
                "price": 5099.00
            }
            """;

    public final static String IMPORT_BOTLAND_ITEM_REQUEST = """
            {
                "url": "http://localhost:9091/raspberry-pi-botland.html"
            }
            """;

    public final static String NEW_SOURCE_REQUEST = BOTLAND_SOURCE_REQUEST;

    private Requests() {
    }
}
