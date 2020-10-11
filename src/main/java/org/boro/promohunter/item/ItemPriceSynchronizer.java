package org.boro.promohunter.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
class ItemPriceSynchronizer {

    private final ItemRepository repository;
    private final ItemImporter importer;

    @Transactional
    @Scheduled(cron = "${promohunter.itempricesynchronizer.cron}")
    public void synchronizePrices() {
        log.info("Started synchronization");

        // @TODO don't fetch all items, use stream instead
        repository.findAll().forEach(this::synchronizePrice);
        log.info("Finished synchronization");
    }

    public void synchronizePrice(Item item) {
        var itemPrice = item.getPrice();
        var sourceName = item.getSource().getName();

        importer.importItem(item.getUrl(), item.getSource())
                .map(Item::getPrice)
                .filter(price -> price.compareTo(itemPrice) != 0)
                .ifPresent(price -> {
                    log.info("Price of {} from {} was changed from {} to {}",
                            item.getName(), sourceName, itemPrice, price);
                    item.setPrice(price);
                });
    }
}
