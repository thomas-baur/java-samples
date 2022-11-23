package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TracedResourceTest {

    @Test
    public void testService1Endpoint() {
        given()
                .when().get("/service1")
                .then()
                .statusCode(200)
                .body(is("this is service 1"));
    }
}
