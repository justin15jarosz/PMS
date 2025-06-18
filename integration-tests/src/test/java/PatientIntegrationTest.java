import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PatientIntegrationTest extends BaseIntegrationUtility {

    @Test
    public void shouldReturnPatientsWithValidToken() {
        String token = authorizeUserAndReturnToken();

        given().header("Authorization", token)
                .when()
                .get("/api/patients")
                .then()
                .statusCode(200)
                .body("patients", notNullValue());
    }
}
