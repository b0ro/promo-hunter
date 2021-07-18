package org.boro.promohunter.api.item;

import io.restassured.response.Response;
import org.boro.promohunter.api.BaseApiTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.boro.promohunter.api.ApiCalls.createItemFrom;
import static org.boro.promohunter.api.ApiCalls.createSourceFrom;
import static org.boro.promohunter.api.ApiCalls.deleteItemWith;
import static org.boro.promohunter.api.ApiCalls.getItemWith;
import static org.boro.promohunter.api.ApiCalls.importItemWith;
import static org.boro.promohunter.api.ApiCalls.listItems;
import static org.boro.promohunter.api.ApiCalls.updateItemWith;
import static org.boro.promohunter.api.Requests.BOTLAND_ITEM2_REQUEST;
import static org.boro.promohunter.api.Requests.BOTLAND_ITEM3_REQUEST;
import static org.boro.promohunter.api.Requests.BOTLAND_ITEM_REQUEST;
import static org.boro.promohunter.api.Requests.BOTLAND_SOURCE_REQUEST;
import static org.boro.promohunter.api.Requests.IMPORT_BOTLAND_ITEM_REQUEST;
import static org.boro.promohunter.api.Requests.MORELE_ITEM_REQUEST;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class ItemApiTest extends BaseApiTest {

    @Test
    void shouldAddItem() throws Exception {
        // given: there is a source
        createSourceFrom(asJSONObject(BOTLAND_SOURCE_REQUEST));

        // and
        var itemRequest = asJSONObject(BOTLAND_ITEM_REQUEST);

        // when
        var itemResponse = createItemFrom(itemRequest);

        // then
        returnsCorrectItem(itemResponse, itemRequest);
    }

    @Test
    void shouldReturnStoredItem() throws Exception {
        // given: there is a source
        createSourceFrom(asJSONObject(BOTLAND_SOURCE_REQUEST));

        // and
        var itemRequest = asJSONObject(BOTLAND_ITEM_REQUEST);

        // and: there is an item
        var itemResponse = createItemFrom(itemRequest);

        // when
        var id = extractId(itemResponse);
        var fetchedItemResponse = getItemWith(id);

        // then
        returnsCorrectItem(fetchedItemResponse, itemRequest);
    }

    @Test
    void shouldUpdateItem() throws Exception {
        // given: there is a source
        createSourceFrom(asJSONObject(BOTLAND_SOURCE_REQUEST));

        // and
        var itemRequest = asJSONObject(BOTLAND_ITEM_REQUEST);
        var updateRequest = asJSONObject(MORELE_ITEM_REQUEST);

        // and: there is an item
        var newItemResponse = createItemFrom(itemRequest);

        // when
        var id = extractId(newItemResponse);
        var fetchedItemResponse = updateItemWith(id, updateRequest);

        // then
        returnsCorrectItem(fetchedItemResponse, updateRequest);

        // and
        var newItemResponseJson = newItemResponse.getBody().asString();
        var fetchedItemResponseJson = fetchedItemResponse.getBody().asString();
        assertThat(newItemResponseJson).isNotEqualTo(fetchedItemResponseJson);
    }

    @Test
    void shouldPresentAllPriceUpdates() throws Exception {
        // given: there is a source
        createSourceFrom(asJSONObject(BOTLAND_SOURCE_REQUEST));

        // and
        var itemRequest = asJSONObject(BOTLAND_ITEM_REQUEST);

        // and
        var updateRequest = asJSONObject(BOTLAND_ITEM_REQUEST);
        updateRequest.put("price", 400);

        // and: there is an item
        var newItemResponse = createItemFrom(itemRequest);

        // when
        var id = extractId(newItemResponse);
        updateItemWith(id, updateRequest);

        // and
        updateRequest.put("price", 450);
        var updatedItemResponse = updateItemWith(id, updateRequest);

        // then
        updatedItemResponse.then().body("priceUpdates.size()", is(3));

        assertThat(extractPrice(updatedItemResponse, "priceUpdates[0].value")).isEqualTo(388);
        assertThat(extractPrice(updatedItemResponse, "priceUpdates[1].value")).isEqualTo(400);
        assertThat(extractPrice(updatedItemResponse, "priceUpdates[2].value")).isEqualTo(450);
    }

    @Test
    void shouldDeleteItem() throws Exception {
        // given: there is a source
        createSourceFrom(asJSONObject(BOTLAND_SOURCE_REQUEST));

        // and
        var itemRequest = asJSONObject(BOTLAND_ITEM_REQUEST);

        // and: there is an item
        var newItemResponse = createItemFrom(itemRequest);

        // when
        var id = extractId(newItemResponse);
        var deletedItemResponse = deleteItemWith(id);

        // then
        deletedItemResponse.then()
            .statusCode(204);

        // and
        var fetchedItemResponse = getItemWith(id);
        fetchedItemResponse.then()
            .statusCode(404);
    }

    @Test
    void shouldImportItem() throws Exception {
        // given: there is a source
        createSourceFrom(asJSONObject(BOTLAND_SOURCE_REQUEST));

        // and
        var importedItemRequest = asJSONObject(IMPORT_BOTLAND_ITEM_REQUEST);
        var expectedItemRequest = asJSONObject(BOTLAND_ITEM_REQUEST);

        // when
        var importedItemResponse = importItemWith(importedItemRequest);

        // then
        returnsCorrectItem(importedItemResponse, expectedItemRequest);
    }

    @Test
    void shouldListItemsLastAddedFirst() throws Exception {
        // given: there is a source
        createSourceFrom(asJSONObject(BOTLAND_SOURCE_REQUEST));

        // and
        var firstResponse = createItemFrom(asJSONObject(BOTLAND_ITEM_REQUEST));
        var secondResponse = createItemFrom(asJSONObject(BOTLAND_ITEM2_REQUEST));
        var thirdResponse = createItemFrom(asJSONObject(BOTLAND_ITEM3_REQUEST));

        // when
        var response = listItems();

        // then
        response.then()
                .body("content[0].id", equalTo(extractId(thirdResponse)))
                .body("content[1].id", equalTo(extractId(secondResponse)))
                .body("content[2].id", equalTo(extractId(firstResponse)));
    }

    void returnsCorrectItem(Response response, JSONObject item) throws JSONException {
        response.then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo(item.get("name")))
                .body("description", equalTo(item.get("description")))
                .body("url", equalTo(item.get("url")))
                .body("createdAt", equalTo(TODAY.toString()))
                .body("lastModifiedAt", equalTo(TODAY.toString()));

        assertThat(extractPrice(response)).isEqualTo(item.get("price"));
    }
}
