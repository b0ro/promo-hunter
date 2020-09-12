package org.boro.promohunter.adapters.api.item.dto;

import org.boro.promohunter.item.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ItemResourceTest {

    private static final String[] ITEM_PROPERTIES = {"id", "name", "description", "url"};

    @Test
    void shouldContainItemData() {
        // given
        var someTime = LocalDateTime.of(LocalDate.of(2020, 4, 1), LocalTime.now());
        var item = item();
        item.setCreatedAt(someTime);
        item.setLastModifiedAt(someTime);

        // when
        var itemResource = ItemResource.of(item);

        // then
        assertThat(itemResource).isEqualToComparingOnlyGivenFields(item, ITEM_PROPERTIES);
        assertThat(itemResource.getPrice()).isEqualTo(item.getPrice().doubleValue());
        assertThat(itemResource.getPriceUpdates()).hasSize(item.getPriceUpdates().size());
        assertThat(itemResource.getCreatedAt()).isEqualTo("2020-04-01");
        assertThat(itemResource.getLastModifiedAt()).isEqualTo("2020-04-01");
    }

    @Test
    void shouldReturnNullOnNullItem() {
        // given
        Item item = null;

        // when
        var itemResource = ItemResource.of(item);

        // then
        assertThat(itemResource).isNull();
    }

    @Test
    void shouldIgnoreDatesWithNullValue() {
        // given
        var item = item();

        // when
        var itemResource = ItemResource.of(item);

        // then
        assertThat(itemResource.getCreatedAt()).isNull();
        assertThat(itemResource.getLastModifiedAt()).isNull();
    }

    private Item item() {
        return Item.of(
                "name",
                "description",
                "https://mediamarkt.pl/konsole-i-gry/konsola-nintendo-switch-joy-con-szary-nowa-wersja-v2",
                BigDecimal.TEN
        );
    }
}
