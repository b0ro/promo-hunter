package org.boro.promohunter.adapters.api.bundle;

import org.boro.promohunter.adapters.api.bundle.dto.BundleRequest;
import org.boro.promohunter.adapters.api.bundle.dto.BundleResource;
import org.boro.promohunter.adapters.api.item.dto.ItemResource;
import org.boro.promohunter.adapters.api.item.dto.PriceUpdateResource;
import org.boro.promohunter.bundle.BundleNotFoundException;
import org.boro.promohunter.item.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = BundleController.class)
class BundleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BundleApiService apiService;

    private static Stream<Arguments> provideErrorBundleRequests() {
        return Stream.of(
                Arguments.of("{\"name\": null, \"description\": null}"),
                Arguments.of("{\"name\": \"\", \"description\": null}"),
                Arguments.of("{\"name\": \"na\", \"description\": null}"),
                Arguments.of("{\"name\": null, \"description\": null}"));
    }

    @Test
    void shouldReturnResourceContainingCreatedBundle() throws Exception {
        // given
        var requestJson = bundleRequestJson();
        var request = bundleRequest();
        var bundleResource = bundleResource();
        when(apiService.create(request)).thenReturn(bundleResource);

        // when
        var result = mockMvc.perform(post("/bundles")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print());

        //then
        assertResponseContainsResource(bundleResource, result);
    }

    @ParameterizedTest
    @MethodSource("provideErrorBundleRequests")
    void shouldReturnReturn400StatusCodeForInvalidName(String requestJson) throws Exception {
        // given method parameters
        // when
        var result = mockMvc.perform(post("/bundles")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print());

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnUpdatedBundle() throws Exception {
        // given
        var requestJson = bundleRequestJson();
        var request = bundleRequest();
        var bundleResource = bundleResource();
        var bundleId = bundleResource.getId();
        when(apiService.update(bundleId, request)).thenReturn(bundleResource);

        // when
        var result = mockMvc.perform(put("/bundles/{bundleId}", bundleId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print());

        // then
        assertResponseContainsResource(bundleResource, result);
    }

    @ParameterizedTest
    @MethodSource("provideErrorBundleRequests")
    void shouldReturnReturn400StatusCodeForInvalidNameForUpdatedBundle(String requestJson) throws Exception {
        // given
        var bundleId = 20;

        // when
        var result = mockMvc.perform(put("/bundles/{bundleId}", bundleId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print());

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn404StatusCodeIfBundleForUpdateWasNotFound() throws Exception {
        // given
        var bundleId = 0;
        var requestJson = bundleRequestJson();
        var request = bundleRequest();
        when(apiService.update(bundleId, request)).thenThrow(new BundleNotFoundException(bundleId));

        // when
        var result = mockMvc.perform(put("/bundles/{bundleId}", bundleId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print());

        // then
        result.andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnResourceForGivenId() throws Exception {
        // given
        var bundleResource = bundleResourceWithItem();
        var bundleId = bundleResource.getId();
        when(apiService.findOne(bundleId)).thenReturn(bundleResource);

        // when
        var result = mockMvc.perform(get("/bundles/{bundleId}", bundleId))
                .andDo(print());

        // then
        assertResponseContainsResourceWithItems(bundleResource, result);
    }

    @Test
    void shouldReturn404StatusCodeIfBundleWasNotFound() throws Exception {
        // given
        var bundleId = 0;
        when(apiService.findOne(bundleId)).thenThrow(new BundleNotFoundException(bundleId));

        // when
        var result = mockMvc.perform(get("/bundles/{bundleId}", bundleId))
                .andDo(print());

        // then
        result.andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAllBundleResources() throws Exception {
        // given
        var bundleResource = bundleResource();
        var bundleResourceWithItem = bundleResourceWithItem();
        var itemResource = bundleResourceWithItem.getItems().iterator().next();
        var priceUpdates = new ArrayList<>(itemResource.getPriceUpdates());
        var bundleResources = List.of(bundleResource, bundleResourceWithItem);
        when(apiService.getAll()).thenReturn(bundleResources);

        // when
        var result = mockMvc.perform(get("/bundles/all"))
                .andDo(print());

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(bundleResource.getId())))
                .andExpect(jsonPath("$[0].name", is(bundleResource.getName())))
                .andExpect(jsonPath("$[0].description", is(bundleResource.getDescription())))
                .andExpect(jsonPath("$[0].price", is(bundleResource.getPrice())))
                .andExpect(jsonPath("$[0].items", is(hasSize(0))))
                .andExpect(jsonPath("$[0].createdAt", is(bundleResource.getCreatedAt())))
                .andExpect(jsonPath("$[0].lastModifiedAt", is(bundleResource.getLastModifiedAt())))

                .andExpect(jsonPath("$[1].id", is(bundleResourceWithItem.getId())))
                .andExpect(jsonPath("$[1].name", is(bundleResourceWithItem.getName())))
                .andExpect(jsonPath("$[1].description", is(bundleResourceWithItem.getDescription())))
                .andExpect(jsonPath("$[1].price", is(bundleResourceWithItem.getPrice())))
                .andExpect(jsonPath("$[1].items", is(hasSize(1))))
                .andExpect(jsonPath("$[1].createdAt", is(bundleResourceWithItem.getCreatedAt())))
                .andExpect(jsonPath("$[1].lastModifiedAt", is(bundleResourceWithItem.getLastModifiedAt())))

                .andExpect(jsonPath("$[1].items[0].id", is(itemResource.getId())))
                .andExpect(jsonPath("$[1].items[0].name", is(itemResource.getName())))
                .andExpect(jsonPath("$[1].items[0].description", is(itemResource.getDescription())))
                .andExpect(jsonPath("$[1].items[0].url", is(itemResource.getUrl())))
                .andExpect(jsonPath("$[1].items[0].price", is(itemResource.getPrice())))

                .andExpect(jsonPath("$[1].items[0].priceUpdates[0].value", is(priceUpdates.get(0).getValue())))
                .andExpect(jsonPath("$[1].items[0].priceUpdates[0].createdAt", is(priceUpdates.get(0).getCreatedAt())))
                .andExpect(jsonPath("$[1].items[0].priceUpdates[1].value", is(priceUpdates.get(1).getValue())))
                .andExpect(jsonPath("$[1].items[0].priceUpdates[1].createdAt", is(priceUpdates.get(1).getCreatedAt())));
    }

    @Test
    void shouldReturnPageOfResources() throws Exception {
        // given
        var bundleResource = bundleResource();
        var bundleResourceWithItem = bundleResourceWithItem();
        var itemResource = bundleResourceWithItem.getItems().iterator().next();
        var priceUpdates = new ArrayList<>(itemResource.getPriceUpdates());
        var bundleResources = List.of(bundleResource, bundleResourceWithItem);
        var pageable = PageRequest.of(0, 20);
        var page = new PageImpl<>(bundleResources, pageable, 500);
        when(apiService.getAll(pageable)).thenReturn(page);

        // when
        var result = mockMvc.perform(get("/bundles")
                .param("page", String.valueOf(pageable.getPageNumber()))
                .param("size", String.valueOf(pageable.getPageSize())))
                .andDo(print());

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(bundleResource.getId())))
                .andExpect(jsonPath("$.content[0].name", is(bundleResource.getName())))
                .andExpect(jsonPath("$.content[0].description", is(bundleResource.getDescription())))
                .andExpect(jsonPath("$.content[0].price", is(bundleResource.getPrice())))
                .andExpect(jsonPath("$.content[0].items", is(hasSize(0))))
                .andExpect(jsonPath("$.content[0].createdAt", is(bundleResource.getCreatedAt())))
                .andExpect(jsonPath("$.content[0].lastModifiedAt", is(bundleResource.getLastModifiedAt())))

                .andExpect(jsonPath("$.content[1].id", is(bundleResourceWithItem.getId())))
                .andExpect(jsonPath("$.content[1].name", is(bundleResourceWithItem.getName())))
                .andExpect(jsonPath("$.content[1].description", is(bundleResourceWithItem.getDescription())))
                .andExpect(jsonPath("$.content[1].price", is(bundleResourceWithItem.getPrice())))
                .andExpect(jsonPath("$.content[1].items", is(hasSize(1))))
                .andExpect(jsonPath("$.content[1].createdAt", is(bundleResourceWithItem.getCreatedAt())))
                .andExpect(jsonPath("$.content[1].lastModifiedAt", is(bundleResourceWithItem.getLastModifiedAt())))

                .andExpect(jsonPath("$.content[1].items[0].id", is(itemResource.getId())))
                .andExpect(jsonPath("$.content[1].items[0].name", is(itemResource.getName())))
                .andExpect(jsonPath("$.content[1].items[0].description", is(itemResource.getDescription())))
                .andExpect(jsonPath("$.content[1].items[0].url", is(itemResource.getUrl())))
                .andExpect(jsonPath("$.content[1].items[0].price", is(itemResource.getPrice())))

                .andExpect(jsonPath("$.content[1].items[0].priceUpdates[0].value", is(priceUpdates.get(0).getValue())))
                .andExpect(jsonPath("$.content[1].items[0].priceUpdates[0].createdAt", is(priceUpdates.get(0).getCreatedAt())))
                .andExpect(jsonPath("$.content[1].items[0].priceUpdates[1].value", is(priceUpdates.get(1).getValue())))
                .andExpect(jsonPath("$.content[1].items[0].priceUpdates[1].createdAt", is(priceUpdates.get(1).getCreatedAt())))

                .andExpect(jsonPath("$.number", is(page.getNumber())))
                .andExpect(jsonPath("$.size", is(page.getSize())))
                .andExpect(jsonPath("$.numberOfElements", is(page.getNumberOfElements())))
                .andExpect(jsonPath("$.totalElements", is((int) page.getTotalElements())));
    }

    @Test
    void shouldReturnResourceAfterAssigningItemToBundle() throws Exception {
        // given
        var bundleResourceWithItem = bundleResourceWithItem();
        var itemResource = bundleResourceWithItem.getItems().iterator().next();
        when(apiService.assignItemToBundle(itemResource.getId(), bundleResourceWithItem.getId()))
                .thenReturn(bundleResourceWithItem);

        // when
        var result = mockMvc.perform(put("/bundles/{bundleId}/items/{itemId}",
                bundleResourceWithItem.getId(), itemResource.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        assertResponseContainsResourceWithItems(bundleResourceWithItem, result);
    }

    @Test
    void shouldReturn404StatusCodeIfItemBeingAppliedToBundleDoesNotExist() throws Exception {
        // given
        var bundleId = 0;
        var itemId = 30;

        doThrow(ItemNotFoundException.class)
                .when(apiService).assignItemToBundle(itemId, bundleId);

        // when
        var result = mockMvc.perform(put("/bundles/{bundleId}/items/{itemId}", bundleId, itemId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        result.andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404StatusCodeIfBundleToWhichItemIsBeingAddedDoesNotExist() throws Exception {
        // given
        var bundleId = 20;
        var itemId = 0;

        doThrow(BundleNotFoundException.class)
                .when(apiService).assignItemToBundle(itemId, bundleId);

        // when
        var result = mockMvc.perform(put("/bundles/{bundleId}/items/{itemId}", bundleId, itemId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        result.andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNoContentStatusAfterDeletingBundle() throws Exception {
        // given
        var bundleId = 20;

        // when
        var result = mockMvc.perform(delete("/bundles/{bundleId}", bundleId))
                .andDo(print());

        // then
        result.andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404StatusCodeIfBundleForDeletionWasNotFound() throws Exception {
        // given
        var bundleId = 0;
        doThrow(BundleNotFoundException.class)
                .when(apiService).delete(bundleId);

        // when
        var result = mockMvc.perform(delete("/bundles/{bundleId}", bundleId))
                .andDo(print());

        // then
        result.andExpect(status().isNotFound());
    }

    private String bundleRequestJson() {
        return "{\"name\": \"name\", \"description\": \"description\"}";
    }

    private BundleRequest bundleRequest() {
        return BundleRequest.builder()
                .name("name")
                .description("description")
                .build();
    }

    private BundleResource bundleResource() {
        var now = Instant.now().toString();
        return BundleResource.builder()
                .id(9)
                .name("name-1")
                .description("description-1")
                .price(0.0)
                .items(Collections.emptySet())
                .createdAt(now)
                .lastModifiedAt(now)
                .build();
    }

    private BundleResource bundleResourceWithItem() {
        var yesterday = Instant.now().minus(1, ChronoUnit.DAYS).toString();
        return BundleResource.builder()
                .id(9)
                .name("name-2")
                .description("description-2")
                .price(0.0)
                .createdAt(yesterday)
                .lastModifiedAt(yesterday)
                .items(Set.of(itemResource()))
                .build();
    }

    private ItemResource itemResource() {
        var today = LocalDate.now();
        var yesterday = today.minusDays(1);

        var previousPrice = PriceUpdateResource.builder()
                .value(1450.99)
                .createdAt(yesterday.toString())
                .build();

        var currentPrice = PriceUpdateResource.builder()
                .value(1499.99)
                .createdAt(today.toString())
                .build();

        return ItemResource.builder()
                .id(30)
                .name("item name")
                .description("item description")
                .url("https://mediamarkt.pl/konsole-i-gry/konsola-nintendo-switch-joy-con-szary-nowa-wersja-v2")
                .price(currentPrice.getValue())
                .priceUpdates(Set.of(previousPrice, currentPrice))
                .build();
    }

    private void assertResponseContainsResource(BundleResource bundleResource, ResultActions result) throws Exception {
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bundleResource.getId())))
                .andExpect(jsonPath("$.name", is(bundleResource.getName())))
                .andExpect(jsonPath("$.description", is(bundleResource.getDescription())))
                .andExpect(jsonPath("$.price", is(bundleResource.getPrice())))
                .andExpect(jsonPath("$.items", hasSize(bundleResource.getItems().size())))
                .andExpect(jsonPath("$.createdAt", is(bundleResource.getCreatedAt())))
                .andExpect(jsonPath("$.lastModifiedAt", is(bundleResource.getLastModifiedAt())));
    }

    private void assertResponseContainsResourceWithItems(BundleResource bundleResource, ResultActions result) throws Exception {
        assertResponseContainsResource(bundleResource, result);

        var itemResource = bundleResource.getItems().iterator().next();
        var priceUpdates = new ArrayList<>(itemResource.getPriceUpdates());

        result.andExpect(jsonPath("$.items[0].id", is(itemResource.getId())))
                .andExpect(jsonPath("$.items[0].name", is(itemResource.getName())))
                .andExpect(jsonPath("$.items[0].description", is(itemResource.getDescription())))
                .andExpect(jsonPath("$.items[0].url", is(itemResource.getUrl())))
                .andExpect(jsonPath("$.items[0].price", is(itemResource.getPrice())))

                .andExpect(jsonPath("$.items[0].priceUpdates[0].value", is(priceUpdates.get(0).getValue())))
                .andExpect(jsonPath("$.items[0].priceUpdates[0].createdAt", is(priceUpdates.get(0).getCreatedAt())))
                .andExpect(jsonPath("$.items[0].priceUpdates[1].value", is(priceUpdates.get(1).getValue())))
                .andExpect(jsonPath("$.items[0].priceUpdates[1].createdAt", is(priceUpdates.get(1).getCreatedAt())));
    }
}
