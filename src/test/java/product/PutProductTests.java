package product;

import category.BaseTests;
import com.github.javafaker.Faker;
import dto.Product;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static endpoints.Endpoints.POST_PRODUCT_ENDPOINT;
import static endpoints.Endpoints.PRODUCT_ID_ENDPOINT;
import static enums.CategoryType.FOOD;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class PutProductTests extends BaseTests {
    Faker faker = new Faker();
    Product product;
    Integer id;
    RequestSpecification putProductRequestSpec;
    ResponseSpecification putProductResponseSpec;
    RequestSpecification postProductRequestSpec;
    ResponseSpecification postProductResponseSpec;


    @BeforeEach
    void setUp() {
        product = Product.builder()
                .price(100)
                .title(faker.food().fruit())
                .categoryTitle(FOOD.getName())
                .id(null)
                .build();
        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        postProductResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectStatusLine("HTTP/1.1 201 ")
                .expectBody("title", equalTo(product.getTitle()))
                .expectBody("price", equalTo(product.getPrice()))
                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
                .build();
        //
        id = given(postProductRequestSpec, postProductResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void putProductPositiveTest() {
        //Refactoring
        product = Product.builder()
                .price(100)
                .title(faker.food().fruit())
                .categoryTitle(FOOD.getName())
                .id(id)
                .build();
        putProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        putProductResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 ")
                .expectBody("price", equalTo(product.getPrice()))
                .expectBody("title", equalTo(product.getTitle()))
                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
                .build();
        id = given(putProductRequestSpec, putProductResponseSpec)
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
        //
    }
    @Test
    void putProductPriceLARGETest() {
        //Refactoring
        product = Product.builder()
                .price(100_000_000)
                .title(faker.food().fruit())
                .categoryTitle(FOOD.getName())
                .id(id)
                .build();
        putProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        putProductResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 ")
                .expectBody("price", equalTo(product.getPrice()))
                .expectBody("title", equalTo(product.getTitle()))
                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
                .build();
        id = given(putProductRequestSpec, putProductResponseSpec)
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
        //
    }

    @Test
    void putProductCategoryTitleNonExistTest() {
        product = Product.builder()
                .price(1234)
                .title("Banana")
                .categoryTitle("Fasdsad")
                .id(id)
                .build();
        putProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        id = given(putProductRequestSpec)
                .expect()
                .statusCode(500)
                .when()
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void putProductTitleUpperMiXEdLowerGETest() {
        //Refactoring
        product = Product.builder()
                .price(1234)
                .title("banana BANANA baNANa")
                .categoryTitle(FOOD.getName())
                .id(id)
                .build();
        putProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        id = given(putProductRequestSpec)
                .expect()
                .statusCode(200)
                .when()
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
        //
    }

    @AfterEach
    void tearDown() {
        if (id != null) {
            given()
                    .response()
                    .spec(deleteResponseSpec)
                    .when()
                    .delete(PRODUCT_ID_ENDPOINT, id)
                    .prettyPeek()
                    .then()
                    .statusCode(200);

        }
    }
}
