package utils;

import io.qameta.allure.*;
import java.util.*;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

/**
 * Генератор тестовых данных пользователя + методы для работы с API.
 */
public class UserGenerator {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api";
    private static final String AUTH_ENDPOINT = BASE_URL + "/auth";
    public static final String LOGIN_URL = AUTH_ENDPOINT + "/login";
    public static final String USER_URL = AUTH_ENDPOINT + "/user";
    public static final String REGISTER_URL = AUTH_ENDPOINT + "/register";
    private static final String VALID_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";

    private String email;
    private String password;
    private String name;
    private String accessToken;

    public UserGenerator(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public String getName() {return name;}
    public String getAccessToken() {return accessToken;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    public void setName(String name) {this.name = name;}
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    //=== Генерация пользователей ===
    @Step("Создание пользователя с валидными данными")
    public static UserGenerator getRandomValidUser() {
        return createUser("test-", "", 6, "User-");
    }
    @Step("Создание пользователя с коротким паролем (<6 символов)")
    public static UserGenerator getUserWithShortPassword() {
        return createUser("test-short-", "", 5, "User-");
    }
    @Step("Создание пользователя с пустым паролем")
    public static UserGenerator getUserWithEmptyPassword() {
        return createUser("test-empty-", "", 0, "User-");
    }

    //=== Методы для работы с API ===
    @Step("Регистрация пользователя через API")
    public UserGenerator registerViaApi() {
        Response response = given()
                .header("Content-type", "application/json")
                .body(this.toJson())
                .post(REGISTER_URL);

        if (response.statusCode() == 200) {
            this.accessToken = response.path("accessToken");
        } else {
            throw new RuntimeException("Ошибка регистрации: " + response.asString());
        }
        return this;
    }

    @Step("Авторизация пользователя через API")
    public UserGenerator loginViaApi() {
        Response response = given()
                .header("Content-type", "application/json")
                .body("{\"email\": \"" + this.email + "\", \"password\": \"" + this.password + "\"}")
                .post(LOGIN_URL);

        if (response.statusCode() == 200) {
            this.accessToken = response.path("accessToken");
        } else {
            throw new RuntimeException("Ошибка авторизации. Код: " + response.statusCode());
        }
        return this;
    }

    @Step("Удаление пользователя через API")
    public void deleteViaApi() {
        if (this.accessToken == null) {
            throw new RuntimeException("Нельзя удалить пользователя: отсутствует accessToken");
        }

        Response response = given()
                .header("Authorization", this.accessToken)
                .delete(USER_URL);

        if (response.statusCode() != 202) {
            throw new RuntimeException("Ошибка удаления пользователя. Код: " + response.statusCode());
        }
    }

    private static UserGenerator createUser(String emailPrefix, String passwordPrefix, int passwordLength, String namePrefix) {
        String randomString = getRandomString(6);
        return new UserGenerator(
                emailPrefix + randomString + "@yandex.ru",
                passwordPrefix + (passwordLength > 0 ? getRandomString(passwordLength) : ""),
                namePrefix + randomString
        );
    }

    //=== Вспомогательные методы ===
    private static String getRandomString(int length) {
        String chars = VALID_CHARS;
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private String toJson() {
        return String.format(
                "{\"email\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"}",
                this.email != null ? this.email : "",
                this.password != null ? this.password : "",
                this.name != null ? this.name : ""
        );
    }
}