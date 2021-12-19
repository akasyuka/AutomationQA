package category;

import com.google.common.collect.ImmutableMap;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static io.restassured.filter.log.LogDetail.*;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.lessThan;

public abstract class BaseTests {
    static Properties properties = new Properties();
    static RequestSpecification logRequestSpecification;
    static ResponseSpecification responseSpecification;
    public static ResponseSpecification deleteResponseSpec;
    static ResponseSpecification categoriesResponseSpec;
    static ResponseSpecification categoriesResponseSpecNotFound;

    @BeforeAll
    static void beforeAll() throws IOException {
        RestAssured.filters(new AllureRestAssured());
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");

        setAllureEnvironment();

        logRequestSpecification = new RequestSpecBuilder()
                .log(METHOD)
                .log(URI)
                .log(BODY)
                .log(HEADERS)
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
        categoriesResponseSpec = new ResponseSpecBuilder()
                .log(ALL)
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectStatusLine(containsStringIgnoringCase("HTTP/1.1 200"))
                .expectResponseTime(lessThan(4L), TimeUnit.SECONDS)
                .build();
        categoriesResponseSpecNotFound = new ResponseSpecBuilder()
                .log(ALL)
                .expectContentType(ContentType.JSON)
                .expectStatusCode(404)
                .expectStatusLine(containsStringIgnoringCase("HTTP/1.1 404"))
                .expectResponseTime(lessThan(4L), TimeUnit.SECONDS)
                .build();
        deleteResponseSpec = new ResponseSpecBuilder()
                .expectContentType("")
                .build();
        RestAssured.requestSpecification = logRequestSpecification;
        RestAssured.responseSpecification = responseSpecification;
    }

    protected static void setAllureEnvironment() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("URL",properties.getProperty("baseURL"))
                        .build());
    }
}
