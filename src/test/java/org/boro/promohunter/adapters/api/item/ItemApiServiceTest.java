package org.boro.promohunter.adapters.api.item;

import org.boro.promohunter.adapters.api.item.dto.ItemRequest;
import org.boro.promohunter.item.Item;
import org.boro.promohunter.item.ItemService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemApiServiceTest {

    private static final String[] ITEM_PROPERTIES = {"id", "name", "description", "url"};

    @Captor
    private ArgumentCaptor<Integer> itemIdCaptor;

    @Mock
    private ItemService service;

    @InjectMocks
    private ItemApiService apiService;

    @Test
    void shouldReturnResourceForCreatedItem() {
        // given
        var request = itemRequest();
        var itemPassed = request.item();
        var itemReturned = item();
        when(service.create(itemPassed)).thenReturn(itemReturned);

        // when
        var itemResource = apiService.create(request);

        // then
        assertThat(itemResource).isEqualToComparingOnlyGivenFields(itemReturned, ITEM_PROPERTIES);
        assertThat(itemResource.getPrice()).isEqualTo(itemReturned.getPrice().doubleValue());
    }

    @Test
    void shouldReturnResourceForCreatedFromUrlItem() {
        // given
        var item = item();
        var url = item.getUrl();
        when(service.createFromUrl(url)).thenReturn(item);

        // when
        var itemResource = apiService.createFromUrl(url);

        // then
        assertThat(itemResource).isEqualToComparingOnlyGivenFields(item, ITEM_PROPERTIES);
        assertThat(itemResource.getPrice()).isEqualTo(item.getPrice().doubleValue());
    }

    @Test
    void shouldReturnResourceForGivenId() {
        // given
        var item = item();
        var itemId = item.getId();
        when(service.findOne(itemId)).thenReturn(item);

        // when
        var itemResource = apiService.findOne(itemId);

        // then
        assertThat(itemResource).isEqualToComparingOnlyGivenFields(item, ITEM_PROPERTIES);
        assertThat(itemResource.getPrice()).isEqualTo(item.getPrice().doubleValue());
    }

    @Test
    void shouldReturnResourceListContainingAllItems() {
        // given
        var items = items();
        when(service.getAll()).thenReturn(items);

        // when
        var response = apiService.getAll();

        // then
        assertThat(response).hasSize(2);
        assertThat(response.get(0)).isEqualToComparingOnlyGivenFields(items.get(0), ITEM_PROPERTIES);
        assertThat(response.get(1)).isEqualToComparingOnlyGivenFields(items.get(1), ITEM_PROPERTIES);
        assertThat(response.get(0).getPrice()).isEqualTo(items.get(0).getPrice().doubleValue());
        assertThat(response.get(1).getPrice()).isEqualTo(items.get(1).getPrice().doubleValue());
    }

    @Test
    void shouldReturnPaginatedResources() {
        // given
        var items = items();
        var pageable = PageRequest.of(0, 2);
        PageImpl<Item> page = new PageImpl<>(items, pageable, 10);
        when(service.getAll(pageable)).thenReturn(page);

        // when
        var response = apiService.getAll(pageable);

        // then
        assertThat(response.getTotalElements()).isEqualTo(page.getTotalElements());

        var content = response.getContent();
        assertThat(content).hasSize(2);
        assertThat(content.get(0)).isEqualToComparingOnlyGivenFields(items.get(0), ITEM_PROPERTIES);
        assertThat(content.get(1)).isEqualToComparingOnlyGivenFields(items.get(1), ITEM_PROPERTIES);
        assertThat(content.get(0).getPrice()).isEqualTo(items.get(0).getPrice().doubleValue());
        assertThat(content.get(1).getPrice()).isEqualTo(items.get(1).getPrice().doubleValue());
    }

    @Test
    void shouldReturnResourceAfterUpdate() {
        // given
        var request = itemRequest();
        var itemPassed = request.item();
        var itemReturned = item();
        var itemId = itemReturned.getId();
        when(service.update(itemId, itemPassed)).thenReturn(itemReturned);

        // when
        var itemResource = apiService.update(itemId, request);

        // then
        assertThat(itemResource).isEqualToComparingOnlyGivenFields(itemReturned, ITEM_PROPERTIES);
        assertThat(itemResource.getPrice()).isEqualTo(itemReturned.getPrice().doubleValue());
    }

    @Test
    void shouldDeleteItem() {
        // given
        var itemId = 20;

        // when
        apiService.delete(itemId);

        // then
        verify(service).delete(itemIdCaptor.capture());
        assertThat(itemIdCaptor.getValue()).isEqualTo(itemId);
    }

    private ItemRequest itemRequest() {
        return ItemRequest.of(
                "name",
                "description",
                "https://mediamarkt.pl/konsole-i-gry/konsola-nintendo-switch-joy-con-szary-nowa-wersja-v2",
                BigDecimal.TEN
        );
    }

    private Item item() {
        var itemId = 20;
        var request = itemRequest();
        return Item.of(itemId, request.getName(), request.getDescription(), request.getUrl(), request.getPrice());
    }

    private List<Item> items() {
        return List.of(
                Item.of(
                        1,
                        "name-1",
                        "description-1",
                        "https://mediamarkt.pl/konsole-i-gry/gra-nintendo-switch-mario-kart-8-deluxe",
                        BigDecimal.ONE
                ),
                Item.of(
                        2,
                        "name-2",
                        "description-2",
                        "https://mediamarkt.pl/konsole-i-gry/gra-nintendo-switch-super-mario-maker-2",
                        BigDecimal.TEN
                )
        );
    }
}
