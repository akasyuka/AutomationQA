package product;

import category.BaseTests;
import dto.Product;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static asserts.IsCategoryExists.isCategoryExists;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;

@Epic("Tests for products")
@Story("Get Product tests")
@Severity(SeverityLevel.CRITICAL)
public class GetProductTests extends BaseTests {
    static Properties properties = new Properties();

    @BeforeEach
    void setUp() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        properties.getProperty("baseURL");
        RestAssured.baseURI = properties.getProperty("baseURL");
    }

    @Test
    @Description("Получить продукт 17713 с проверкой обычного типа")
    void getProductPositiveTest() {
        Product response = when()
                .get("/products/17713")
                .prettyPeek()
                        .body()
                                .as(Product.class);
        assertThat(response.getCategoryTitle(), isCategoryExists());
    }
}
