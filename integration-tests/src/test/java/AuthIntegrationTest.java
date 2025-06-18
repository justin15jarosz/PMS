import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest extends BaseIntegrationUtility {

    @Test
    public void shouldReturnOKWithValidToken() {
        String loginPayload = """
                {
                    "email": "testuser@test.com",
                    "password": "password123"
                }
                """;
        Response response = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract()
                .response();

        System.out.println("Generated Token: " + response.jsonPath().getString("token"));
    }

    @Test
    public void shouldReturnUnauthorizedWithInvalidLogin() {
        String loginPayload = """
                {
                    "email": "testuser@test.com",
                    "password": "password"
                }
                """;

        given().contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401)
                .extract()
                .response();
    }

    @Test
    public void shouldReturnOkWithValidToken() {
        String token = authorizeUserAndReturnToken();

        given().header("Authorization", token)
                .when()
                .get("/auth/validate")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldReturnUnauthorizedWithInvalidToken() {
        String token = "Bearer fsdf@3sdfs";
        given().header("Authorization", token)
                .when()
                .get("/auth/validate")
                .then()
                .statusCode(401)
                .extract()
                .response();
    }
}
