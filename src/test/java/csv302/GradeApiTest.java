package csv302;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class GradeApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void getAllPostsTest() {

        Response response =
                given()
                        .when()
                        .get("/posts")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        Assert.assertTrue(response.jsonPath().getList("$").size() > 0);
    }

    @Test
    public void getSinglePostTest() {

        Response response =
                given()
                        .when()
                        .get("/posts/1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        int id = response.jsonPath().getInt("id");
        String title = response.jsonPath().getString("title");

        Assert.assertEquals(id, 1);
        Assert.assertFalse(title.isEmpty());
    }

    @Test
    public void createPostTest() {

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("title", "API Testing with REST Assured");
        requestBody.put("body", "This is a sample body");
        requestBody.put("userId", 1);

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .post("/posts")
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();

        String returnedTitle = response.jsonPath().getString("title");

        Assert.assertEquals(returnedTitle,
                "API Testing with REST Assured");
    }

    @Test
    public void getInvalidPostTest() {

        given()
                .when()
                .get("/posts/99999")
                .then()
                .statusCode(404);
    }

    @Test
    public void deletePostTest() {

        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
    }
}