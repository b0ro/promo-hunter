package org.boro.promohunter.source.api;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SourceRequestTest {

    private static final String[] REQUEST_PROPERTIES =
            {"name", "description", "url", "nameSelector", "descriptionSelector", "priceSelector"};

    @Test
    void shouldContainCorrectData() {
        // given
        var name = "name";
        var description = "description";
        var url = "https://www.x-kom.pl";
        var nameSelector = "name_selector";
        var descriptionSelector = "description_selector";
        var priceSelector = "price_selector";

        // when
        var request = SourceRequest.of(name, description, url, nameSelector, descriptionSelector,
                priceSelector);

        // then
        assertThat(request.getName()).isEqualTo(name);
        assertThat(request.getDescription()).isEqualTo(description);
        assertThat(request.getUrl()).isEqualTo(url);
        assertThat(request.getNameSelector()).isEqualTo(nameSelector);
        assertThat(request.getDescriptionSelector()).isEqualTo(descriptionSelector);
        assertThat(request.getPriceSelector()).isEqualTo(priceSelector);
    }

    @Test
    void shouldMapToSource() {
        // given
        var request = SourceRequest.of(
                "name",
                "description",
                "https://www.x-kom.pl",
                "name_selector",
                "description_selector",
                "price_selector"
        );

        // when
        var source = request.source();

        // then
        assertThat(source).isEqualToComparingOnlyGivenFields(request, REQUEST_PROPERTIES);
    }
}
