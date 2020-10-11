package org.boro.promohunter.bundle;

import org.boro.promohunter.item.Item;
import org.boro.promohunter.item.PriceUpdate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
class BundleTest {

    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TOMORROW = LocalDate.now().plusDays(1);
    private static final LocalDate IN_2_DAYS = LocalDate.now().plusDays(2);
    private static final LocalDate IN_3_DAYS = LocalDate.now().plusDays(3);

    @Test
    void shouldReturnCorrectSumsForEachItemPriceUpdate() {
        // given
        var bundle = new Bundle(TODAY);
        bundle.setItems(itemsWithPriceUpdates());

        // when
        var priceUpdates = new ArrayList<>(bundle.getPriceUpdates());

        // then
        assertThat(priceUpdates.size()).isEqualTo(3);
        assertThatValueAndCreatedAtEquals(priceUpdates.get(0), new BigDecimal("10.0000"), TODAY);
        assertThatValueAndCreatedAtEquals(priceUpdates.get(1), new BigDecimal("11.0000"), TOMORROW);
        assertThatValueAndCreatedAtEquals(priceUpdates.get(2), new BigDecimal("14.0000"), IN_3_DAYS);
    }

    @Test
    void shouldReturnOnlyPriceUpdatesSinceBundleWasCreated() {
        // given
        var bundle = new Bundle(IN_3_DAYS);
        bundle.setItems(itemsWithPriceUpdates());

        // when
        var priceUpdates = new ArrayList<>(bundle.getPriceUpdates());

        // then
        assertThat(priceUpdates.size()).isEqualTo(1);
        assertThatValueAndCreatedAtEquals(priceUpdates.get(0), new BigDecimal("14.0000"), IN_3_DAYS);
    }

    @Test
    void shouldDisplayInitialPriceForItemsCreatedBeforeBundle() {
        // given
        var bundle = new Bundle(IN_2_DAYS);
        bundle.setItems(itemsWithPriceUpdates());

        // when
        var priceUpdates = new ArrayList<>(bundle.getPriceUpdates());

        // then
        assertThat(priceUpdates.size()).isEqualTo(2);
        assertThatValueAndCreatedAtEquals(priceUpdates.get(0), new BigDecimal("11.0000"), IN_2_DAYS);
        assertThatValueAndCreatedAtEquals(priceUpdates.get(1), new BigDecimal("14.0000"), IN_3_DAYS);
    }

    private Set<Item> itemsWithPriceUpdates() {
        var item1 = new Item(1);
        var priceUpdates1 = Set.of(
                new PriceUpdate(1, new BigDecimal("9.0000"), TODAY),
                new PriceUpdate(2, new BigDecimal("8.0000"), TOMORROW),
                new PriceUpdate(3, new BigDecimal("7.0000"), IN_3_DAYS)
        );
        item1.setPriceUpdates(priceUpdates1);

        var item2 = new Item(2);
        var priceUpdates2 = Set.of(
                new PriceUpdate(1, new BigDecimal("1.0000"), TODAY),
                new PriceUpdate(2, new BigDecimal("3.0000"), TOMORROW),
                new PriceUpdate(3, new BigDecimal("7.0000"), IN_3_DAYS)
        );
        item2.setPriceUpdates(priceUpdates2);

        return Set.of(item1, item2);
    }

    private void assertThatValueAndCreatedAtEquals(
            PriceUpdate priceUpdate,
            BigDecimal expectedValue,
            LocalDate expectedCreatedAt
    ) {
        assertThat(priceUpdate.getValue()).isEqualTo(expectedValue);
        assertThat(priceUpdate.getCreatedAt()).isEqualTo(expectedCreatedAt);
    }
}
