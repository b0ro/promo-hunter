package org.boro.promohunter.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.boro.PromohunterApplication;
import org.boro.promohunter.config.IntegrationConfiguration;
import org.boro.promohunter.infrastructure.DatabaseCleanup;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import java.time.LocalDate;

import static io.restassured.RestAssured.filters;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        classes = {PromohunterApplication.class, IntegrationConfiguration.class},
        webEnvironment = RANDOM_PORT,
        properties = "spring.profiles.active=integration")
@AutoConfigureWireMock(port = 9091)
public abstract class BaseApiTest {

    protected final LocalDate TODAY = LocalDate.now();

    String serverBaseUri = "http://localhost";

    @LocalServerPort
    int serverPort;

    @Autowired
    DatabaseCleanup databaseCleanup;

    @BeforeAll
    static void init() {
        filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());
    }

    @BeforeEach
    void initBeforeEach() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
            .setBaseUri(serverBaseUri)
            .setPort(serverPort)
            .setContentType(ContentType.JSON)
            .build();
    }

    @AfterEach
    void cleanupAfterEach() {
        databaseCleanup.truncateTables();
    }

    public static JSONObject asJSONObject(String json) throws JSONException {
        return new JSONObject(json);
    }
}
