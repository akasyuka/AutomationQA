import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class PostProductTests {
    Product product;
    Integer id;

    @BeforeEach
    void setUp() {
    }

    @Test
    void postProductPositiveTest() {
        product = Product.builder()
                .price(100)
                .title("Banana")
                .categoryTitle("Food")
                .id(null)
                .build();
        id = given()
                .body(product.toString())
                .header("content-type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post("http://80.78.248.82:8189/market/api/v1/products")
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductPriceUnderZeroTest() {
        product = Product.builder()
                .price(-10)
                .title("Banana")
                .categoryTitle("Food")
                .id(null)
                .build();
        id = given()
                .body(product.toString())
                .header("content-type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(401)
                .when()
                .post("http://80.78.248.82:8189/market/api/v1/products")
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductPriceZeroFirstTest() {
        product = Product.builder()
                .price(010)
                .title("Banana")
                .categoryTitle("Food")
                .id(null)
                .build();
        id = given()
                .body(product.toString())
                .header("content-type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(401)
                .when()
                .post("http://80.78.248.82:8189/market/api/v1/products")
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductPriceZeroTest() {
        product = Product.builder()
                .price(0)
                .title("Banana")
                .categoryTitle("Food")
                .id(null)
                .build();
        id = given()
                .body(product.toString())
                .header("content-type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(401)
                .when()
                .post("http://80.78.248.82:8189/market/api/v1/products")
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductPriceNullTest() {
        product = Product.builder()
                .price(null)
                .title("Banana")
                .categoryTitle("Food")
                .id(null)
                .build();
        id = given()
                .body(product.toString())
                .header("content-type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(401)
                .when()
                .post("http://80.78.248.82:8189/market/api/v1/products")
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductPriceLARGETest() {
        product = Product.builder()
                .price(100_000_000)
                .title("Banana")
                .categoryTitle("Food")
                .id(null)
                .build();
        id = given()
                .body(product.toString())
                .header("content-type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post("http://80.78.248.82:8189/market/api/v1/products")
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductTitleUpperMiXEdLowerGETest() {
        product = Product.builder()
                .price(1234)
                .title("banana BANANA baNANa")
                .categoryTitle("Food")
                .id(null)
                .build();
        id = given()
                .body(product.toString())
                .header("content-type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post("http://80.78.248.82:8189/market/api/v1/products")
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductCategoryTitleRandomTest() {
        product = Product.builder()
                .price(1234)
                .title("Banana")
                .categoryTitle("Fasdsad")
                .id(null)
                .build();
        id = given()
                .body(product.toString())
                .header("content-type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(401)
                .when()
                .post("http://80.78.248.82:8189/market/api/v1/products")
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @AfterEach
    void tearDown() {
        when()
                .delete("http://80.78.248.82:8189/market/api/v1/products/{id}", id)
                .prettyPeek()
                .then()
                .statusCode(200);

    }
}
