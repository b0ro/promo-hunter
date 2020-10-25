package org.boro.promohunter.bundle.api;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BundleRequestTest {

    private static final String[] REQUEST_PROPERTIES = {"name", "description"};

    @Test
    void shouldContainCorrectData() {
        // given
        var name  = "name";
        var description = "description";

        // when
        var request = BundleRequest.of(name, description);

        // then
        assertThat(request.getName()).isEqualTo(name);
        assertThat(request.getDescription()).isEqualTo(description);
    }

    @Test
    void shouldMapToBundle() {
        // given
        var request = BundleRequest.of("name", "description");

        // when
        var bundle = request.bundle();

        // then
        assertThat(bundle).isEqualToComparingOnlyGivenFields(request, REQUEST_PROPERTIES);
    }
}
