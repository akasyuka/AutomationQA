package product;

import category.BaseTests;
import com.github.javafaker.Faker;
import dto.Product;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static endpoints.Endpoints.POST_PRODUCT_ENDPOINT;
import static enums.CategoryType.FOOD;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class PostProductTests extends BaseTests {
    Faker faker = new Faker();
    Product product;
    Integer id;
    RequestSpecification postProductRequestSpec;
    ResponseSpecification postProductResponseSpec;


    @BeforeEach
    void setUp() {
//TODO:

//        product = Product.builder()
//                .price(100)
//                .title(faker.food().fruit())
//                .categoryTitle(FOOD.getName())
//                .id(null)
//                .build();
//        postProductRequestSpec = new RequestSpecBuilder()
//                .setBody(product)
//                .setContentType(ContentType.JSON)
//                .build();
//        postProductResponseSpec = new ResponseSpecBuilder()
//                .expectStatusCode(201)
//                .expectStatusLine("HTTP/1.1 201 ")
//                .expectBody("price", equalTo(product.getPrice()))
//                .expectBody("title", equalTo(product.getTitle()))
//                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
//                .build();
    }

    @Test
    void postProductPositiveTest() {
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
                .expectBody("price", equalTo(product.getPrice()))
                .expectBody("title", equalTo(product.getTitle()))
                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
                .build();
        id = given(postProductRequestSpec, postProductResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductWithDifferentAssertsPositiveTest() {
        product = Product.builder()
                .price(100)
                .title("Banana")
                .categoryTitle(FOOD.getName())
                .id(null)
                .build();
        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        Product response = given(postProductRequestSpec)
                .expect()
                .statusCode(201)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        MatcherAssert.assertThat(response.getId(), is(not(nullValue())));
        MatcherAssert.assertThat(response.getPrice(), equalTo(product.getPrice()));
        MatcherAssert.assertThat(response.getTitle(), equalTo(product.getTitle()));
        MatcherAssert.assertThat(response.getCategoryTitle(), equalTo(product.getCategoryTitle()));
    }

    @Test
    void postProductPriceUnderZeroTest() {
        product = Product.builder()
                .price(-10)
                .title("Banana")
                .categoryTitle(FOOD.getName())
                .id(null)
                .build();
        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        id = given(postProductRequestSpec)
                .expect()
                .statusCode(401)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductPriceZeroFirstTest() {
        product = Product.builder()
                .price(010)
                .title("Banana")
                .categoryTitle(FOOD.getName())
                .id(null)
                .build();
        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        id = given(postProductRequestSpec)
                .expect()
                .statusCode(401)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductPriceZeroTest() {
        product = Product.builder()
                .price(0)
                .title("Banana")
                .categoryTitle(FOOD.getName())
                .id(null)
                .build();
        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        id = given(postProductRequestSpec)
                .expect()
                .statusCode(401)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductPriceNullTest() {
        product = Product.builder()
                .price(null)
                .title("Banana")
                .categoryTitle(FOOD.getName())
                .id(null)
                .build();
        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        id = given(postProductRequestSpec)
                .expect()
                .statusCode(401)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductPriceLARGETest() {
        product = Product.builder()
                .price(100_000_000)
                .title("Banana")
                .categoryTitle(FOOD.getName())
                .id(null)
                .build();
        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        id = given(postProductRequestSpec)
                .expect()
                .statusCode(201)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductTitleUpperMiXEdLowerGETest() {
        product = Product.builder()
                .price(1234)
                .title("banana BANANA baNANa")
                .categoryTitle(FOOD.getName())
                .id(null)
                .build();
        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        id = given(postProductRequestSpec)
                .expect()
                .statusCode(201)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductCategoryTitleNonExistTest() {
        product = Product.builder()
                .price(1234)
                .title("Banana")
                .categoryTitle("Fasdsad")
                .id(null)
                .build();
        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        id = given(postProductRequestSpec)
                .expect()
                .statusCode(500)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @AfterEach
    void tearDown() {
        if (id != null) {
            given()
                    .response()
                    .spec(deleteResponseSpec)
                    .when()
                    .delete("http://80.78.248.82:8189/market/api/v1/products/{id}", id)
                    .prettyPeek()
                    .then()
                    .statusCode(200);

        }
    }
}
