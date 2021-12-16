package product;

import category.BaseTests;
import com.github.javafaker.Faker;
import dto.Product;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;

import static endpoints.Endpoints.POST_PRODUCT_ENDPOINT;
import static enums.CategoryType.FOOD;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class PutProductTests extends BaseTests {
    Faker faker = new Faker();
    Product product;
    Integer id;
    RequestSpecification putProductRequestSpec;
    ResponseSpecification putProductResponseSpec;

    @Test
    void putProductPositiveTest() {
        product = Product.builder()
                .price(100)
                .title(faker.food().fruit())
                .categoryTitle(FOOD.getName())
                .id(18427)
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
    }

}
