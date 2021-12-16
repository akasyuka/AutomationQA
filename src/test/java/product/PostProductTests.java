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
    Product.ProductBuilder productBuilder;
    Integer id;
    RequestSpecification postProductRequestSpec;
    ResponseSpecification postProductResponseSpec;
    ResponseSpecification postProductResponseSpecNotFound;


    @BeforeEach
    void setUp() {
        productBuilder = Product.builder()
                .price(100)
                .title(faker.food().dish())
                .categoryTitle(FOOD.getName());

        product = productBuilder.build();

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
        postProductResponseSpecNotFound = new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectStatusLine("HTTP/1.1 401 ")
                .expectBody("title", equalTo(product.getTitle()))
                .expectBody("price", equalTo(product.getPrice()))
                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
                .build();
    }

    @Test
    void postProductPositiveTest() {
        id = given(postProductRequestSpec, postProductResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductWithDifferentAssertsPositiveTest() {
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
        Product product = productBuilder.price(-10).build();
        id = given(postProductRequestSpec, postProductResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
//        postProductPositiveAssert(product, response);
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
