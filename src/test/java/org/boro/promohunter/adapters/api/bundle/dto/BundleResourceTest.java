package org.boro.promohunter.adapters.api.bundle.dto;

import org.boro.promohunter.bundle.Bundle;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class BundleResourceTest {

    private static final String[] BUNDLE_PROPERTIES = {"id", "name", "description"};

    @Test
    void shouldContainAllBundleData() {
        // given
        var someTime = LocalDateTime.of(LocalDate.of(2020, 4, 1), LocalTime.now());
        var bundle = bundle();
        bundle.setCreatedAt(someTime);
        bundle.setLastModifiedAt(someTime);

        // when
        var bundleResource = BundleResource.of(bundle);

        // then
        assertThat(bundleResource).isEqualToComparingOnlyGivenFields(bundle, BUNDLE_PROPERTIES);
        assertThat(bundleResource.getCreatedAt()).isEqualTo("2020-04-01");
        assertThat(bundleResource.getLastModifiedAt()).isEqualTo("2020-04-01");
    }

    @Test
    void shouldReturnNullOnNullBundle() {
        // given
        Bundle bundle = null;

        // when
        var bundleResource = BundleResource.of(bundle);

        // then
        assertThat(bundleResource).isNull();
    }

    @Test
    void shouldIgnoreDatesWithNullValue() {
        // given
        var bundle = bundle();

        // when
        var bundleResource = BundleResource.of(bundle);

        // then
        assertThat(bundleResource.getCreatedAt()).isNull();
        assertThat(bundleResource.getLastModifiedAt()).isNull();
    }

    private Bundle bundle() {
        return  Bundle.of(1, "name", "description");
    }


}
