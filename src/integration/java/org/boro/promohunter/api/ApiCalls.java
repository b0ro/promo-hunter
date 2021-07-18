package org.boro.promohunter.api;

import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public interface ApiCalls {

    String SOURCES_URL = "/sources";
    String ITEMS_URL = "/items";

    static Response createSourceFrom(JSONObject body) {
        return createFrom(body, SOURCES_URL);
    }

    static Response getSourceWith(String id) {
        return getFrom(sourcesUrlWith(id));
    }

    static Response updateSourceWith(String id, JSONObject body) {
        return updateWith(body, sourcesUrlWith(id));
    }

    static Response checkSourceSelectorsWith(JSONObject body) {
        return given()
                .body(body.toString())
            .when()
                .post(SOURCES_URL + "/check");
    }

    static Response listSources() {
        return getFrom(SOURCES_URL);
    }

    static Response deleteSourceWith(String id) {
        return deleteFrom(sourcesUrlWith(id));
    }

    static Response createItemFrom(JSONObject body) {
        return createFrom(body, ITEMS_URL);
    }

    static Response getItemWith(String id) {
        return getFrom(itemsUrlWith(id));
    }

    static Response updateItemWith(String id, JSONObject body) {
        return updateWith(body, itemsUrlWith(id));
    }

    static Response deleteItemWith(String id) {
        return deleteFrom(itemsUrlWith(id));
    }

    static Response importItemWith(JSONObject body) {
        return given()
                .body(body.toString())
            .when()
                .post(ITEMS_URL + "/import");
    }

    static Response listItems() {
        return getFrom(ITEMS_URL);
    }

    private static Response createFrom(JSONObject body, String url) {
        return given()
            .body(body.toString())
                .when()
            .post(url);
    }

    static Response getFrom(String url) {
        return given().when().get(url);
    }

    private static Response updateWith(JSONObject body, String url) {
        return given()
                .body(body.toString())
            .when()
                .put(url);
    }

    private static Response deleteFrom(String url) {
        return given().when().delete(url);
    }

    private static String sourcesUrlWith(String id) {
        return SOURCES_URL + "/" + id;
    }

    private static String itemsUrlWith(String id) {
        return ITEMS_URL + "/" + id;
    }
}
