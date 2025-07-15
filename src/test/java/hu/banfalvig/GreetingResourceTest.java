package hu.banfalvig;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin", "User"})
    void testHelloEndpoint() {
        given()
                .when().get("/hello")
                .then()
                .statusCode(200)
                .body(is("Hello from quarkus"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin", "User"})
    @JwtSecurity(claims = {
            @Claim(key = "birthdate", value = "2001-07-13")
    })
    void testSecurityEndpoint() {
        given()
                .when().get("/security")
                .then()
                .statusCode(200)
                .body(is("hello testUser, isHttps: false, authScheme: null, hasJWT: true birthDay: 2001-07-13"));
    }

    @Test
    void testGreetingEndpoint() {
        String uuid = UUID.randomUUID().toString();
        given()
                .pathParam("name", uuid)
                .when().get("/greeting/{name}")
                .then()
                .statusCode(200)
                .body(is("hello " + uuid));
    }

}