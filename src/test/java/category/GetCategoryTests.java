package category;

import dto.Category;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static endpoints.Endpoints.CATEGORY_ENDPOINT;
import static enums.CategoryType.FOOD;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetCategoryTests extends BaseTests {

    @Test
    void getCategoryTest() {
        given()
                .response()
                .spec(categoriesResponseSpec)
                .when()
                .get(CATEGORY_ENDPOINT, 1);
    }

    @Test
    void getCategoryWithLogsTest() {
        given()
                .response()
                .spec(categoriesResponseSpec)
                .when()
                .get(CATEGORY_ENDPOINT, 1)
                .prettyPeek();
    }

    @Test
    void getCategoryWithAssertsTest() {
        given()
                .response()
                .spec(categoriesResponseSpec)
                .when()
                .get(CATEGORY_ENDPOINT, 1)
                .prettyPeek()
                .then()
                .body("id", equalTo(1));
    }

    @Test
    void getCategoryWithAssertsForResponseTest() {
        Response response = given()
                .response()
                .spec(categoriesResponseSpec)
                .when()
                .get(CATEGORY_ENDPOINT, 1)
                .prettyPeek();
        Category responseBody = response.body().as(Category.class);
        assertThat(response.body().jsonPath().get("products[0].categoryTitle"), equalTo("Food"));
        assertThat(responseBody.getProducts().get(0).getCategoryTitle(), equalTo("Food"));
    }

    @Test
    void getCategoryWithAssertsAfterTestForResponseTest() {
        Category response =given()
                .response()
                .spec(categoriesResponseSpec)
                .when()
                .get(CATEGORY_ENDPOINT, FOOD.getId())
                .prettyPeek()
                .body()
                .as(Category.class);
        response.getProducts().forEach(
                e -> assertThat(e.getCategoryTitle(), equalTo(FOOD.getName()))
        );
        assertThat(response.getTitle(), equalTo(FOOD.getName()));
        assertThat(response.getId(), equalTo(FOOD.getId()));
    }
}
