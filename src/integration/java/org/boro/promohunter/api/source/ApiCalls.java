package org.boro.promohunter.api.source;

import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public interface ApiCalls {

    String SOURCES_URL = "/sources";

    static Response createSourceFrom(JSONObject body) {
        return given()
                .body(body.toString())
            .when()
                .post(SOURCES_URL);
    }

    static Response getSourceWith(String id) {
        return given()
            .when()
                .get(sourcesUrlWith(id));
    }

    static Response updateSourceWith(String id, JSONObject body) {
        return given()
                .body(body.toString())
            .when()
                .put(sourcesUrlWith(id));
    }

    static Response checkSourceSelectorsWith(JSONObject body) {
        return given()
                .body(body.toString())
            .when()
                .post(SOURCES_URL + "/check");
    }

    static Response listSources() {
        return given()
            .when()
                .get(SOURCES_URL);
    }

    static Response deleteSourceWith(String id) {
        return given()
            .when()
                .delete(sourcesUrlWith(id));
    }

    static String sourcesUrlWith(String id) {
        return SOURCES_URL + "/" + id;
    }
}
