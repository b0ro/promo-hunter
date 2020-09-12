package org.boro.promohunter.adapters.api.bundle;

import org.boro.promohunter.adapters.api.bundle.dto.BundleRequest;
import org.boro.promohunter.bundle.Bundle;
import org.boro.promohunter.bundle.BundleService;
import org.boro.promohunter.item.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BundleApiServiceTest {

    private static final String[] BUNDLE_PROPERTIES = {"id", "name", "description"};
    private static final String[] ITEM_PROPERTIES = {"id", "name", "description", "url"};

    @Captor
    private ArgumentCaptor<Integer> bundleIdCaptor;

    @Mock
    private BundleService service;

    @InjectMocks
    private BundleApiService apiService;

    @Test
    void shouldReturnResourceForCreatedBundle() {
        // given
        var bundleId = 20;
        var request = bundleRequest();
        var bundlePassed = Bundle.of(request.getName(), request.getDescription());
        var bundleReturned = Bundle.of(bundleId, request.getName(), request.getDescription());
        when(service.create(bundlePassed)).thenReturn(bundleReturned);

        // when
        var bundleResource = apiService.create(request);

        // then
        assertThat(bundleResource).isEqualToComparingOnlyGivenFields(bundleReturned, BUNDLE_PROPERTIES);
        assertThat(bundleResource.getPrice()).isEqualTo(bundleReturned.getPrice().doubleValue());
        assertThat(bundleResource.getId()).isEqualTo(bundleId);
    }

    @Test
    void shouldReturnResourceForGivenId() {
        // given
        var bundleId = 20;
        var bundle = Bundle.of(bundleId, "name", "description");
        when(service.findOne(bundleId)).thenReturn(bundle);

        // when
        var bundleResource = apiService.findOne(bundleId);

        // then
        assertThat(bundleResource).isEqualToComparingOnlyGivenFields(bundle, BUNDLE_PROPERTIES);
        assertThat(bundleResource.getPrice()).isEqualTo(bundle.getPrice().doubleValue());
    }

    @Test
    void shouldReturnResourceListContainingAllBundles() {
        // given
        var bundles = bundles();
        when(service.getAll()).thenReturn(bundles);

        // when
        var response = apiService.getAll();

        // then
        assertThat(response).hasSize(2);
        assertThat(response.get(0)).isEqualToComparingOnlyGivenFields(bundles.get(0), BUNDLE_PROPERTIES);
        assertThat(response.get(1)).isEqualToComparingOnlyGivenFields(bundles.get(1), BUNDLE_PROPERTIES);
        assertThat(response.get(0).getPrice()).isEqualTo(bundles.get(0).getPrice().doubleValue());
        assertThat(response.get(1).getPrice()).isEqualTo(bundles.get(1).getPrice().doubleValue());

    }

    @Test
    void shouldReturnPaginatedResources() {
        // given
        var bundles = bundles();
        var pageable = PageRequest.of(0, 2);
        PageImpl<Bundle> page = new PageImpl<>(bundles, pageable, 10);
        when(service.getAll(pageable)).thenReturn(page);

        // when
        var response = apiService.getAll(pageable);

        // then
        assertThat(response.getTotalElements()).isEqualTo(page.getTotalElements());

        var content = response.getContent();
        assertThat(content).hasSize(2);
        assertThat(content.get(0)).isEqualToComparingOnlyGivenFields(bundles.get(0), BUNDLE_PROPERTIES);
        assertThat(content.get(1)).isEqualToComparingOnlyGivenFields(bundles.get(1), BUNDLE_PROPERTIES);
        assertThat(content.get(0).getPrice()).isEqualTo(bundles.get(0).getPrice().doubleValue());
        assertThat(content.get(1).getPrice()).isEqualTo(bundles.get(1).getPrice().doubleValue());
    }

    @Test
    void shouldReturnResourceAfterUpdate() {
        // given
        var bundleId = 20;
        var request = bundleRequest();
        var bundlePassed = Bundle.of(request.getName(), request.getDescription());
        var bundleReturned = Bundle.of(bundleId, request.getName(), request.getDescription());
        when(service.update(bundleId, bundlePassed)).thenReturn(bundleReturned);

        // when
        var bundleResource = apiService.update(bundleId, request);

        // then
        assertThat(bundleResource).isEqualToComparingOnlyGivenFields(bundleReturned, BUNDLE_PROPERTIES);
        assertThat(bundleResource.getPrice()).isEqualTo(bundleReturned.getPrice().doubleValue());
    }

    @Test
    void shouldAssignItemToBundle() {
        // given
        var bundleId = 20;
        var itemId = 30;

        var item = item();
        var bundle = Bundle.of(bundleId, "name", "description", Set.of(item));

        when(service.assignItemToBundle(itemId, bundleId)).thenReturn(bundle);

        // when
        var bundleResource = apiService.assignItemToBundle(itemId, bundleId);

        // then
        assertThat(bundleResource).isEqualToComparingOnlyGivenFields(bundle, BUNDLE_PROPERTIES);
        assertThat(bundleResource.getPrice()).isEqualTo(bundle.getPrice().doubleValue());
        assertThat(bundleResource.getItems()).hasSize(1);

        var itemResource = bundleResource.getItems().iterator().next();
        assertThat(itemResource).isEqualToComparingOnlyGivenFields(item, ITEM_PROPERTIES);
        assertThat(itemResource.getPrice()).isEqualTo(item.getPrice().doubleValue());
    }

    @Test
    void shouldDeleteBundle() {
        // given
        var bundleId = 20;

        // when
        apiService.delete(bundleId);

        // then
        verify(service).delete(bundleIdCaptor.capture());
        assertThat(bundleIdCaptor.getValue()).isEqualTo(bundleId);
    }

    private BundleRequest bundleRequest() {
        return BundleRequest.of("bundle name", "bundle description");
    }

    private List<Bundle> bundles() {
        return List.of(
                Bundle.of(1, "name 1", "description 1"),
                Bundle.of(2, "name 2", "description 2")
        );
    }

    private Item item() {
        return Item.of(
                1,
                "name",
                "description",
                "https://mediamarkt.pl/konsole-i-gry/konsola-nintendo-switch-joy-con-szary-nowa-wersja-v2",
                new BigDecimal("99.99")
        );
    }
}
