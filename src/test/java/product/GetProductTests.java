package product;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.when;

public class GetProductTests {
    static Properties properties = new Properties();

    @BeforeEach
    void setUp() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        properties.getProperty("baseURL");
        RestAssured.baseURI = properties.getProperty("baseURL");
    }

    @Test
    void getProductPositiveTest() {
        when()
                .get("/products/17713")
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
