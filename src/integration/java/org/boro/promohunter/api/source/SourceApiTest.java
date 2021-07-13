package org.boro.promohunter.api.source;

import io.restassured.response.Response;
import org.assertj.core.data.Percentage;
import org.boro.promohunter.api.BaseApiTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.boro.promohunter.api.source.ApiCalls.checkSourceSelectorsWith;
import static org.boro.promohunter.api.source.ApiCalls.createSourceFrom;
import static org.boro.promohunter.api.source.ApiCalls.deleteSourceWith;
import static org.boro.promohunter.api.source.ApiCalls.getSourceWith;
import static org.boro.promohunter.api.source.ApiCalls.listSources;
import static org.boro.promohunter.api.source.ApiCalls.updateSourceWith;
import static org.boro.promohunter.api.source.Requests.BOTLAND_SOURCE_REQUEST;
import static org.boro.promohunter.api.source.Requests.CHECK_BOTLAND_SOURCE_REQUEST;
import static org.boro.promohunter.api.source.Requests.CHECK_MORELE_SOURCE_REQUEST;
import static org.boro.promohunter.api.source.Requests.CHECK_XKOM_SOURCE_REQUEST;
import static org.boro.promohunter.api.source.Requests.MORELE_SOURCE_REQUEST;
import static org.boro.promohunter.api.source.Requests.NEW_SOURCE_REQUEST;
import static org.boro.promohunter.api.source.Requests.XKOM_SOURCE_REQUEST;
import static org.boro.promohunter.api.source.Responses.CHECK_BOTLAND_SOURCE_RESPONSE;
import static org.boro.promohunter.api.source.Responses.CHECK_MORELE_SOURCE_RESPONSE;
import static org.boro.promohunter.api.source.Responses.CHECK_XKOM_SOURCE_RESPONSE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class SourceApiTest extends BaseApiTest {

    @Test
    void shouldAddSource() throws Exception {
        // given
        var request = asJSONObject(NEW_SOURCE_REQUEST);

        // when
        var newSourceResponse = createSourceFrom(request);

        // then
        returnsCorrectSource(newSourceResponse, request);
    }

    @Test
    void shouldReturnStoredSource() throws Exception {
        // given
        var request = asJSONObject(NEW_SOURCE_REQUEST);

        // and
        var newSourceResponse = createSourceFrom(request);

        // when
        String id = extractId(newSourceResponse);
        var fetchedSourceResponse = getSourceWith(id);

        // then
        returnsCorrectSource(newSourceResponse, request);

        // and
        returnsCorrectSource(fetchedSourceResponse, request);
    }

    @Test
    void shouldUpdateSource() throws Exception {
        // given
        var request = asJSONObject(BOTLAND_SOURCE_REQUEST);
        var updateRequest = asJSONObject(MORELE_SOURCE_REQUEST);

        // and
        var newSourceResponse = createSourceFrom(request);

        // when
        String id = extractId(newSourceResponse);
        var updatedSourceResponse = updateSourceWith(id, updateRequest);

        // then
        returnsCorrectSource(updatedSourceResponse, updateRequest);

        // and
        var newSourceResponseJson = newSourceResponse.getBody().asString();
        var updatedSourceResponseJson = updatedSourceResponse.getBody().asString();
        assertThat(newSourceResponseJson).isNotEqualTo(updatedSourceResponseJson);
    }


    @Test
    void shouldDeleteSource() throws Exception {
        // given
        var request = asJSONObject(NEW_SOURCE_REQUEST);

        // and
        var newSourceResponse = createSourceFrom(request);

        // when
        String id = extractId(newSourceResponse);
        var deletedSourceResponse = deleteSourceWith(id);

        // then
        deletedSourceResponse.then()
            .statusCode(204);

        // and
        var fetchedSourceResponse = getSourceWith(id);
        fetchedSourceResponse.then()
            .statusCode(404);
    }

    @ParameterizedTest
    @MethodSource("checkSourceSelectorsDatasource")
    void shouldCheckSourceSelectors(String uri, JSONObject request, JSONObject expectedResponse) throws Exception {
        // given params
        givenThat(get(urlEqualTo("/" + uri))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "text/html;charset=utf-8")
                    .withBodyFile(uri)
            )
        );

        // when
        var response = checkSourceSelectorsWith(request);

        // then
        response.then()
            .statusCode(200)
            .body("name", equalTo(expectedResponse.get("name")))
            .body("description", equalTo(expectedResponse.get("description")))
            .body("url", equalTo(expectedResponse.get("url")));

        assertThat(extractPrice(response)).isCloseTo(
            expectedResponse.getDouble("price"),
            Percentage.withPercentage(0.01)
        );
    }

    @Test
    void shouldListSourcesLastAddedFirst() throws Exception {
        // given
        var firstResponse = createSourceFrom(asJSONObject(BOTLAND_SOURCE_REQUEST));
        var secondResponse = createSourceFrom(asJSONObject(XKOM_SOURCE_REQUEST));
        var thirdResponse =createSourceFrom(asJSONObject(MORELE_SOURCE_REQUEST));

        // when
        var response = listSources();

        // then
        response.then()
            .body("content[0].id", equalTo(extractId(thirdResponse)))
            .body("content[1].id", equalTo(extractId(secondResponse)))
            .body("content[2].id", equalTo(extractId(firstResponse)));
    }

    static Stream<Arguments> checkSourceSelectorsDatasource() throws Exception {
        return Stream.of(
            Arguments.of(
                "karta-graficzna-morele.html",
                asJSONObject(CHECK_MORELE_SOURCE_REQUEST),
                asJSONObject(CHECK_MORELE_SOURCE_RESPONSE)
            ),
            Arguments.of(
                "raspberry-pi-botland.html",
                asJSONObject(CHECK_BOTLAND_SOURCE_REQUEST),
                asJSONObject(CHECK_BOTLAND_SOURCE_RESPONSE)
            ),
            Arguments.of(
                "karta-graficzna-xkom.html",
                asJSONObject(CHECK_XKOM_SOURCE_REQUEST),
                asJSONObject(CHECK_XKOM_SOURCE_RESPONSE)
            )
        );
    }

    String extractId(Response response) {
        return response.path("id").toString();
    }
    Double extractPrice(Response response) {
        return Double.parseDouble(response.path("price").toString());
    }

    void returnsCorrectSource(Response response, JSONObject source ) throws JSONException {
        response.then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("name", equalTo(source.get("name")))
            .body("description", equalTo(source.get("description")))
            .body("url", equalTo(source.get("url")))
            .body("nameSelector", equalTo(source.get("nameSelector")))
            .body("descriptionSelector", equalTo(source.get("descriptionSelector")))
            .body("priceSelector", equalTo(source.get("priceSelector")))
            .body("createdAt", equalTo(TODAY.toString()))
            .body("lastModifiedAt", equalTo(TODAY.toString()));
    }
}
