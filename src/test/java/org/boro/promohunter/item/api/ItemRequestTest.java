package org.boro.promohunter.item.api;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRequestTest {

    private static final String[] REQUEST_PROPERTIES = {"name", "description", "url", "price"};

    @Test
    void shouldContainCorrectData() {
        // given
        var name  = "name";
        var description = "description";
        var url = "https://mediamarkt.pl/konsole-i-gry/konsola-nintendo-switch-joy-con-szary-nowa-wersja-v2";
        var price = BigDecimal.TEN;

        // when
        var request = ItemRequest.of(name, description, url, price);

        // then
        assertThat(request.getName()).isEqualTo(name);
        assertThat(request.getDescription()).isEqualTo(description);
        assertThat(request.getUrl()).isEqualTo(url);
        assertThat(request.getPrice()).isEqualTo(price);
    }

    @Test
    void shouldMapToItem() {
        // given
        var request = ItemRequest.of(
                "name",
                "description",
                "https://mediamarkt.pl/konsole-i-gry/konsola-nintendo-switch-joy-con-szary-nowa-wersja-v2",
                BigDecimal.TEN
        );

        // when
        var item = request.item();

        // then
        assertThat(item).isEqualToComparingOnlyGivenFields(request, REQUEST_PROPERTIES);
    }
}
