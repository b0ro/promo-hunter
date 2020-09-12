package org.boro.promohunter.adapters.api.item;

import org.boro.promohunter.adapters.api.item.dto.ItemRequest;
import org.boro.promohunter.adapters.api.item.dto.ItemResource;
import org.boro.promohunter.adapters.api.item.dto.PriceUpdateResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemApiService apiService;

    private static Stream<Arguments> provideErrorItemRequests() {
        return null;
    }

    @Test
    void shouldReturnResourceContainingCreatedItem() throws Exception {

    }

    @ParameterizedTest
    @MethodSource("provideErrorItemRequests")
    void shouldReturnReturn400StatusCodeForInvalidRequest(String requestJson) throws Exception {

    }

    @Test
    void shouldReturn400StatusCodeIfItemWithGivenUrlAlreadyExist() {

    }

    // @todo add unique url validation

    @Test
    void shouldReturnUpdatedItem() throws Exception {

    }

    @ParameterizedTest
    @MethodSource("provideErrorItemRequests")
    void shouldReturnReturn400StatusCodeForInvalidRequestForUpdatedItem(String requestJson) throws Exception {

    }

    @Test
    void shouldReturn404StatusCodeIfItemForUpdateWasNotFound() throws Exception {

    }

    @Test
    void shouldReturnResourceForGivenId() throws Exception {

    }

    @Test
    void shouldReturn404StatusCodeIfItemWasNotFound() throws Exception {

    }

    @Test
    void shouldReturnAllItemResources() throws Exception {

    }

    @Test
    void shouldReturnPageOfResources() throws Exception {

    }

    @Test
    void shouldReturnNoContentStatusAfterDeletingItem() throws Exception {

    }

    @Test
    void shouldReturn404StatusCodeIfItemForDeletionWasNotFound() throws Exception {

    }

    private String itemRequestJson() {
        return "{\n" +
                "  \"name\": \"name\",\n" +
                "  \"description\": \"description\",\n" +
                "  \"url\": \"https://mediamarkt.pl/konsole-i-gry/konsola-nintendo-switch-joy-con-szary-nowa-wersja-v2\",\n" +
                "  \"price\": 10\n" +
                "}";
    }

    private ItemRequest itemRequest() {
        return ItemRequest.builder()
                .name("name")
                .description("description")
                .url("https://mediamarkt.pl/konsole-i-gry/konsola-nintendo-switch-joy-con-szary-nowa-wersja-v2")
                .price(BigDecimal.TEN)
                .build();
    }

    private ItemResource itemResource() {
        var currentPrice = PriceUpdateResource.builder()
                .value(10.0)
                .createdAt(LocalDate.now().toString())
                .build();

        return ItemResource.builder()
                .id(30)
                .name("name")
                .description("description")
                .url("https://mediamarkt.pl/konsole-i-gry/konsola-nintendo-switch-joy-con-szary-nowa-wersja-v2")
                .price(currentPrice.getValue())
                .priceUpdates(Set.of(currentPrice))
                .build();
    }
}
