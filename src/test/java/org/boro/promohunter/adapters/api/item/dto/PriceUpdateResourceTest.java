package org.boro.promohunter.adapters.api.item.dto;

import org.boro.promohunter.item.PriceUpdate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class PriceUpdateResourceTest {

    @Test
    void shouldContainPriceUpdateData() {
        // given
        var createdAt = LocalDateTime.of(LocalDate.of(2020, Month.JANUARY, 1), LocalTime.now());
        var priceUpdate = new PriceUpdate(BigDecimal.TEN, createdAt);

        // when
        var priceUpdateResource = PriceUpdateResource.of(priceUpdate);

        // then
        assertThat(priceUpdateResource.getValue()).isEqualTo(priceUpdate.getValue().doubleValue());
        assertThat(priceUpdateResource.getCreatedAt()).isEqualTo("2020-01-01");
    }

    @Test
    void shouldReturnNullOnNullPriceUpdate() {
        // given
        PriceUpdate priceUpdate = null;

        // when
        var priceUpdateResource = PriceUpdateResource.of(priceUpdate);

        // then
        assertThat(priceUpdateResource).isNull();
    }

    @Test
    void shouldIgnoreCreatedAtNullValue() {
        // given
        var priceUpdate = new PriceUpdate(BigDecimal.TEN);

        // when
        var priceUpdateResource = PriceUpdateResource.of(priceUpdate);

        // then
        assertThat(priceUpdateResource.getValue()).isEqualTo(priceUpdate.getValue().doubleValue());
        assertThat(priceUpdateResource.getCreatedAt()).isNull();
    }
}
