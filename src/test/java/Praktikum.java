import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Praktikum {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-mesto.praktikum-services.ru";
    }

    @Test
    public void registrationAndAuth() {
        // Составили email
        Random random = new Random();
        String email = "something" + random.nextInt(10000000) + "@yandex.ru";
        // составь json, используя переменную email. Не забудь про экранизацию кавычек с помощью '/'
        String json = "{\"email\": \"" + email + "\", \"password\": \"password123123123\"}";
        System.out.println(json);
// POST запрос на регистрацию signup
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/signup")
                .then()
        // проверь статус ответа
                .statusCode(201);

        // POST запрос на авторизацию signin с теми же параметрами
        Response response = given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/signin");

        response.then().assertThat()
        // проверь, что пришедший в ответ токен не пустой
                .body("token", notNullValue()).and().statusCode(200);
        // проверь статус ответа
// Попытка зарегистрироваться с теми же параметрами ещё раз
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/signup")
                .then()
                // проверь статус ответа
                .statusCode(409);
    }
}