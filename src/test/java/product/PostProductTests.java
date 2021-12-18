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

import static asserts.CommonAsserts.postProductPositiveAssert;
import static endpoints.Endpoints.POST_PRODUCT_ENDPOINT;
import static endpoints.Endpoints.PRODUCT_ID_ENDPOINT;
import static enums.CategoryType.FOOD;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class PostProductTests extends BaseTests {
    Faker faker = new Faker();
    Product product;
    //    Product.ProductBuilder productBuilder;
    Integer id;
    RequestSpecification postProductRequestSpec;
    ResponseSpecification postProductResponseSpec;
    ResponseSpecification postProductResponseSpecUnauthorized;


    @BeforeEach
    void setUp() {
//        productBuilder = Product.builder()
//                .price(100)
//                .title(faker.food().dish())
//                .categoryTitle(FOOD.getName());
//
//        product = productBuilder.build();
//
//        postProductRequestSpec = new RequestSpecBuilder()
//                .setBody(product)
//                .setContentType(ContentType.JSON)
//                .build();
//
//        postProductResponseSpec = new ResponseSpecBuilder()
//                .expectStatusCode(201)
//                .expectStatusLine("HTTP/1.1 201 ")
//                .expectBody("title", equalTo(product.getTitle()))
//                .expectBody("price", equalTo(product.getPrice()))
//                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
//                .build();
//
//        postProductResponseSpecUnauthorized = new ResponseSpecBuilder()
//                .expectStatusCode(401)
//                .expectStatusLine("HTTP/1.1 401 ")
//                .expectBody("title", equalTo(product.getTitle()))
//                .expectBody("price", equalTo(product.getPrice()))
//                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
//                .build();
    }

    @Test
    void postProductPositiveTest() {
        //Refactoring
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
    void postProductWithDifferentAssertsPositiveTest() {
        //Refactoring
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
        Product response = given(postProductRequestSpec, postProductResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .body()
                .as(Product.class);
        postProductPositiveAssert(product, response);
    }

    @Test
    void postProductPriceUnderZeroTest() {
// Refactoring version
//        product.setPrice(-10);
//        Product product = productBuilder.price(-10).build();
//        Product response = given()
//                .body(product)
//                .expect()
//                .spec(postProductResponseSpecNotFound)
//                .when()
//                .post(POST_PRODUCT_ENDPOINT)
//                .prettyPeek()
//                .body()
//                .as(Product.class);
//        postProductPositiveAssert(product, response);

        //Refactoring
        product = Product.builder()
                .price(-10)
                .title(faker.food().fruit())
                .categoryTitle(FOOD.getName())
                .id(null)
                .build();
        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        postProductResponseSpecUnauthorized = new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectStatusLine("HTTP/1.1 401 ")
                .expectBody("title", equalTo(product.getTitle()))
                .expectBody("price", equalTo(product.getPrice()))
                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
                .build();
        //
        id = given(postProductRequestSpec, postProductResponseSpecUnauthorized)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductPriceZeroFirstTest() {
        //Refactoring
        product = Product.builder()
                .price(010)
                .title(faker.food().fruit())
                .categoryTitle(FOOD.getName())
                .id(null)
                .build();
        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        postProductResponseSpecUnauthorized = new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectStatusLine("HTTP/1.1 401 ")
                .expectBody("title", equalTo(product.getTitle()))
                .expectBody("price", equalTo(product.getPrice()))
                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
                .build();
        //
        id = given(postProductRequestSpec, postProductResponseSpecUnauthorized)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductPriceZeroTest() {
        //Refactoring
        product = Product.builder()
                .price(0)
                .title(faker.food().fruit())
                .categoryTitle(FOOD.getName())
                .id(null)
                .build();
        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        postProductResponseSpecUnauthorized = new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectStatusLine("HTTP/1.1 401 ")
                .expectBody("title", equalTo(product.getTitle()))
                .expectBody("price", equalTo(product.getPrice()))
                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
                .build();
        //
        id = given(postProductRequestSpec, postProductResponseSpecUnauthorized)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductPriceNullTest() {
        //Refactoring
        product = Product.builder()
                .price(null)
                .title(faker.food().fruit())
                .categoryTitle(FOOD.getName())
                .id(null)
                .build();
        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        postProductResponseSpecUnauthorized = new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectStatusLine("HTTP/1.1 401 ")
                .expectBody("title", equalTo(product.getTitle()))
                .expectBody("price", equalTo(product.getPrice()))
                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
                .build();
        //
        id = given(postProductRequestSpec, postProductResponseSpecUnauthorized)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductPriceLARGETest() {
        //Refactoring
        product = Product.builder()
                .price(100_000_000)
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
    void postProductTitleUpperMiXEdLowerGETest() {
        //Refactoring
        product = Product.builder()
                .price(100_000_000)
                .title("banana BANANA baNANa")
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

    @Test
    void postProductTitleNullTest() {
        //Refactoring
        product = Product.builder()
                .price(100)
                .title(null)
                .categoryTitle(FOOD.getName())
                .id(null)
                .build();
        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        postProductResponseSpecUnauthorized = new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectStatusLine("HTTP/1.1 401 ")
                .expectBody("title", equalTo(product.getTitle()))
                .expectBody("price", equalTo(product.getPrice()))
                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
                .build();
        //
        id = given(postProductRequestSpec, postProductResponseSpecUnauthorized)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    //401 or 400
    @Test
    void postProductPriceStringTest() {
        //Refactoring
        product = Product.builder()
                .price("Hello")
                .title(faker.food().fruit())
                .categoryTitle(FOOD.getName())
                .id(null)
                .build();
        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();
        postProductResponseSpecUnauthorized = new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectStatusLine("HTTP/1.1 401 ")
                .expectBody("title", equalTo(product.getTitle()))
                .expectBody("price", equalTo(product.getPrice()))
                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
                .build();
        //
        id = given(postProductRequestSpec, postProductResponseSpecUnauthorized)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductPriceFloatTest() {
        //Refactoring
        product = Product.builder()
                .price(12.13)
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
    void postProductCategoryTitleAnyRegister() {
        //Refactoring
        product = Product.builder()
                .price(12)
                .title(faker.food().fruit())
                .categoryTitle("food")
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
