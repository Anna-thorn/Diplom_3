package api;

import com.google.gson.JsonObject;
import io.qameta.allure.*;
import io.restassured.response.Response;
import io.restassured.specification.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

import model.User;
import utils.Urls;

/**
 * Класс для работы с API пользователя
 */
public class UserApi {
    private static final String AUTH_ENDPOINT = Urls.API_BASE_URL + "/auth";
    public static final String LOGIN_URL = AUTH_ENDPOINT + "/login";
    public static final String USER_URL = AUTH_ENDPOINT + "/user";
    public static final String REGISTER_URL = AUTH_ENDPOINT + "/register";

    private static final RequestSpecification REQ_SPEC = given()
            .baseUri(Urls.API_BASE_URL)
            .header("Content-type", "application/json");

    @Step("Регистрация пользователя через API")
    public Response registerUser(User user) {
        return REQ_SPEC
                .body(user.toJson())
                .post(REGISTER_URL);
    }

    @Step("Получить accessToken после авторизации")
    public String extractAccessToken(Response response) {
        return response.then()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");
    }

    @Step("Полная регистрация пользователя")
    public User registerAndGetUser(User user) {
        Response response = registerUser(user);
        user.setAccessToken(extractAccessToken(response));
        return user;
    }

    @Step("Авторизация пользователя через API")
    public Response loginUser(User user) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("email", user.getEmail());
        requestBody.addProperty("password", user.getPassword());

        return REQ_SPEC
                .body(requestBody.toString())
                .post(LOGIN_URL);
    }

    @Step("Удаление пользователя через API")
    public Response deleteUser(User user) {
        return REQ_SPEC
                .header("Authorization", user.getAccessToken())
                .delete(USER_URL);
    }
}