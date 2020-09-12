package org.boro.promohunter.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boro.promohunter.source.Source;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemImporter {

    public Optional<Item> importItem(String url, Source source) {
        try {
            var item = Item.of(url, source);
            var document = Jsoup.connect(url).get();

            // @todo throw exception when cannot extract
            extract(document, source.getNameSelector()).ifPresent(item::setName);
            extract(document, source.getDescriptionSelector()).ifPresent(item::setDescription);
            extract(document, source.getPriceSelector())
                    .map(s -> s.replace(",", "."))
                    .map(s -> s.replaceAll("[^\\d.]", ""))
                    .filter(s -> !s.isBlank())
                    .ifPresent(priceText -> item.setPrice(new BigDecimal(priceText)));

            return Optional.of(item);
        } catch (IOException exception) {
            log.warn("Could not open {}", url);
            return Optional.empty();
        }
    }

    private Optional<String> extract(Document document, String selector) {
        // ignore when selector is empty
        if (selector.isBlank()) {
            return Optional.empty();
        }
        return Optional.ofNullable(document.select(selector).first())
                .map(Element::text);
    }
}

