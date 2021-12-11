import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryTests {
    public static final String CATEGORY_ENDPOINT ="categories/{id}";
    static Properties properties = new Properties();
    @BeforeEach
    void setUp() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        properties.getProperty("baseURL");
        RestAssured.baseURI = properties.getProperty("baseURL");
    }

    @Test
    public void getCategoryProductsTest() {
        Response response = given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .get(CATEGORY_ENDPOINT, 1)
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.body().jsonPath().get("products[0].categoryTitle"), equalTo("Food"));
    }
}
